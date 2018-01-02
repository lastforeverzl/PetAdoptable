package com.zackyzhang.petadoptable.data

import com.zackyzhang.petadoptable.data.mapper.ShelterMapper
import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.source.SheltersDataStoreFactory
import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.petadoptable.domain.repository.SheltersRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Provides an implementation of the [SheltersRepository] interface for communicating to and from
 * data sources
 */
class SheltersDataRepository @Inject constructor(private val factory: SheltersDataStoreFactory,
                                                 private val shelterMapper: ShelterMapper) :
        SheltersRepository {

    override fun clearShelters(): Completable {
        return factory.retrieveCacheDataStore().clearShelters()
    }

    override fun saveShelters(pets: List<Shelter>): Completable {
        val petEntities = mutableListOf<ShelterEntity>()
        pets.map { petEntities.add(shelterMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().saveShelters(petEntities)
    }

    override fun getShelters(options: Map<String, String>): Flowable<List<Shelter>> {
        //TODO("Disable cache shelters just for now.")
        return factory.retrieveRemoteDataStore().getShelters(options)
                .flatMap {
                    Flowable.just(it.map { shelterMapper.mapFromEntity(it) })
                }
//        return factory.retrieveCacheDataStore().isCached()
//                .flatMapPublisher {
//                    factory.retrieveDataStore(it, options["offset"].toString()).getShelters(options)
//                }
//                .flatMap {
//                    Flowable.just(it.map { shelterMapper.mapFromEntity(it) })
//                }
//                .flatMap {
//                    saveShelters(it).toSingle { it }.toFlowable()
//                }
    }

    override fun getShelterById(options: Map<String, String>): Single<Shelter> {
        return factory.retrieveRemoteDataStore().getShelterById(options)
                .map { shelterMapper.mapFromEntity(it) }
    }
}

