package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.PetEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the caching of Pets. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface PetsCache {

    /**
     * Clear all Pets from the cache
     */
    fun clearPets(): Completable

    /**
     * Save a given list of Pets to the cache
     */
    fun savePets(pets: List<PetEntity>): Completable

    /**
     * Retrieve a list of Pets, from the cache
     */
    fun getPets(animal: String): Flowable<List<PetEntity>>

    /**
     * Update pet
     */
    fun updatePet(pet: PetEntity): Completable

    /**
     * Retrieve a list of Favorite pets from the DB
     */
    fun getFavoritePets(): Flowable<List<PetEntity>>

    /**
     * Retrieve a pet by id
     */
    fun getPetById(id: String): Single<PetEntity>

    /**
     * Checks whether there are Pets exists in the cache.
     */
    fun isCached(animal: String): Single<Boolean>

    /**
     * Set a point in time at when the cache was last updated.
     */
    fun setLastCacheTime(lastCache: Long)

    /**
     * Checks if the cache is expired
     */
    fun isExpired(): Boolean
}