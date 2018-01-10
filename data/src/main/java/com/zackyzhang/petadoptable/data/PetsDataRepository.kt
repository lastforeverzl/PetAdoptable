package com.zackyzhang.petadoptable.data

import com.zackyzhang.petadoptable.data.mapper.PetMapper
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.source.PetsDataStoreFactory
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Provides an implementation of the [PetsRepository] interface for communicating to and from
 * data sources
 */
class PetsDataRepository @Inject constructor(private val factory: PetsDataStoreFactory,
                                             private val petMapper: PetMapper) :
        PetsRepository {

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

    override fun getFavoritePets(): Flowable<List<Pet>> {
        return factory.retrieveCacheDataStore().getFavoritePets()
                .flatMap {
                    Flowable.just(it.map { petMapper.mapFromEntity(it) })
                }
    }

    override fun saveToFavorite(pet: Pet): Completable {
        return factory.retrieveCacheDataStore().saveToFavorite(petMapper.mapToEntity(pet))
    }

    override fun removeFromFavorite(pet: Pet): Completable {
        return factory.retrieveCacheDataStore().removeFromFavorite(petMapper.mapToEntity(pet))
    }

    override fun isFavoritePet(id: String): Single<Boolean> {
        return factory.retrieveCacheDataStore().isFavoritePet(id)
    }

    override fun getPetById(options: Map<String, String>): Single<Pet> {
        return factory.retrieveRemoteDataStore().getPetById(options)
                .map { petMapper.mapFromEntity(it) }
    }

    override fun getShelterPets(options: Map<String, String>): Flowable<List<Pet>> {
        return factory.retrieveRemoteDataStore().getShelterPets(options)
                .flatMap {
                    Flowable.just(it.map { petMapper.mapFromEntity(it) })
                }
    }

    override fun getSearchPets(options: Map<String, String>): Flowable<List<Pet>> {
        return factory.retrieveRemoteDataStore().getPets(options)
                .flatMap {
                    Flowable.just(it.map { petMapper.mapFromEntity(it) })
                }
    }

}
