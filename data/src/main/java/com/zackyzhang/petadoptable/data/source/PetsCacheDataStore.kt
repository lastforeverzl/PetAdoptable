package com.zackyzhang.petadoptable.data.source

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsCache
import com.zackyzhang.petadoptable.data.repository.PetsDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [PetsDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class PetsCacheDataStore @Inject constructor(private val petsCache: PetsCache):
        PetsDataStore {

    /**
     * Clear all Pets from the cache
     */
    override fun clearPets(): Completable {
        return petsCache.clearPets()
                .doOnComplete { println("pets in database cleared!") }
    }

    /**
     * Save a given [List] of [PetEntity] instances to the cache
     */
    override fun savePets(pets: List<PetEntity>): Completable {
        return petsCache.savePets(pets)
                .doOnComplete {
                    petsCache.setLastCacheTime(System.currentTimeMillis())
//                    petsCache.setCached()
                    println("pets saved")
                }
    }

    /**
     * Retrieve a list of [PetEntity] instance from the cache
     */
    override fun getPets(options: Map<String, String>): Flowable<List<PetEntity>> {
        return petsCache.getPets(options["animal"].toString())
    }

    override fun getFavoritePets(): Flowable<List<PetEntity>> {
        return petsCache.getFavoritePets()
    }

    override fun saveToFavorite(pet: PetEntity): Completable {
        return petsCache.saveFavoritePet(pet)
                .doOnComplete { println("pet save to favorite!") }
    }

    override fun removeFromFavorite(pet: PetEntity): Completable {
        return petsCache.removeFavoritePet(pet)
                .doOnComplete { println("pet remove from favorite!") }
    }

    override fun isFavoritePet(id: String): Single<Boolean> {
        return petsCache.isFavoritePet(id)
    }

    override fun getPetById(options: Map<String, String>): Single<PetEntity> {
        return petsCache.getPetById(options["id"]!!)
    }

    /**
     * Check the cache status for given animal
     */
    override fun isCached(animal: String): Single<Boolean> {
        return petsCache.isCached(animal)
    }

    override fun getShelterPets(options: Map<String, String>): Flowable<List<PetEntity>> {
        throw UnsupportedOperationException()
    }

}