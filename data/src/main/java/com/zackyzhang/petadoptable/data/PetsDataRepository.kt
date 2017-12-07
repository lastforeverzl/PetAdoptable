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
                                             private val petsMapper: PetMapper) :
        PetsRepository {
    override fun clearPets(): Completable {
        return factory.retrieveCacheDataStore().clearPets()
    }

    override fun savePets(pets: List<Pet>): Completable {
        val petEntities = mutableListOf<PetEntity>()
        pets.map { petEntities.add(petsMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().savePets(petEntities)
    }

    override fun getPets(key: String, location: String, options: Map<String, String>):
            Flowable<List<Pet>> {
        return factory.retrieveCacheDataStore().isCached()
                .flatMapPublisher {
                    factory.retrieveDataStore(it).getPets(key, location, options)
                }
                .flatMap {
                    Flowable.just(it.map { petsMapper.mapFromEntity(it) })
                }
                .flatMap {
                    savePets(it).toSingle { it }.toFlowable()
                }
    }

}
