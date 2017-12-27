package com.zackyzhang.petadoptable.data

import com.zackyzhang.petadoptable.data.mapper.PetMapper
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.source.PetsDataStoreFactory
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Provides an implementation of the [PetsRepository] interface for communicating to and from
 * data sources
 */
class PetsDataRepository @Inject constructor(private val factory: PetsDataStoreFactory,
                                             private val petMapper: PetMapper) :
        PetsRepository {

    /**
     * todo: Need to implement Service for clear cached pets whenever the cache expired.
     */
    override fun clearPets(): Completable {
        return factory.retrieveCacheDataStore().clearPets()
    }

    override fun savePets(pets: List<Pet>): Completable {
        val petEntities = mutableListOf<PetEntity>()
        pets.map { petEntities.add(petMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().savePets(petEntities)
    }

    override fun getPets(options: Map<String, String>):
            Flowable<List<Pet>> {
//        val dataStore = factory.retrieveDataStore(options["offset"].toString())
//        return dataStore.getPets(options)
//                .flatMap {
//                    Flowable.just(it.map { petsMapper.mapFromEntity(it) })
//                }
//                .flatMap {
//                    if (dataStore is PetsRemoteDataStore) {
//                        savePets(it).toSingle { it }.toFlowable()
////                        clearPets().toFlowable()
//                    } else {
//                        Flowable.just(it)
//                    }
//                }

        return factory.retrieveCacheDataStore().isCached(options["animal"].toString())
                .flatMapPublisher {
                    factory.retrieveDataStore(it, options["offset"].toString()).getPets(options)
                }
                .flatMap {
                    Flowable.just(it.map { petMapper.mapFromEntity(it) })
                }
                .flatMap {
                    savePets(it).toSingle { it }.toFlowable()
                }
    }

}
