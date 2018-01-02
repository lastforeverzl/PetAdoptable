package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the caching of Shelters. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface SheltersCache {

    /**
     * Clear all Shelters from the cache
     */
    fun clearShelters(): Completable

    /**
     * Save a given list of Shelters to the cache
     */
    fun saveShelters(shelters: List<ShelterEntity>): Completable

    /**
     * Retrive a list of Shelters, from the cache
     */
    fun getShelters(): Flowable<List<ShelterEntity>>

    /**
     * Checks whether there are Shelters exists in the cache.
     */
    fun isCached(): Single<Boolean>

    /**
     * Set a point in time at when the cache was last updated.
     */
    fun setLastCacheTime(lastCache: Long)

    /**
     * Checks if the cache is expired
     */
    fun isExpired(): Boolean

    /**
     * Retrieve a shelter by id
     */
//    fun getShelterById(id: String): Single<ShelterEntity>

}
