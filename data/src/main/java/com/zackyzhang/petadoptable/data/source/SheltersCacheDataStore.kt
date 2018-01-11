package com.zackyzhang.petadoptable.data.source

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.repository.SheltersCache
import com.zackyzhang.petadoptable.data.repository.SheltersDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [SheltersDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class SheltersCacheDataStore @Inject constructor(private val sheltersCache: SheltersCache) :
        SheltersDataStore {

    /**
     * Clear all Shelters from the cache
     */
    override fun clearShelters(): Completable {
        return sheltersCache.clearShelters()
                .doOnComplete { println("Shelters in database cleared!") }
    }

    /**
     * Save a given [List] of [ShelterEntity] instances to the cache
     */
    override fun saveShelters(shelters: List<ShelterEntity>): Completable {
        return sheltersCache.saveShelters(shelters)
                .doOnComplete {
                    sheltersCache.setLastCacheTime(System.currentTimeMillis())
                    println("shelters saved")
                }
    }

    /**
     * Retrieve a list of [ShelterEntity] instance from the cache
     */
    override fun getShelters(options: Map<String, String>): Flowable<List<ShelterEntity>> {
        return sheltersCache.getShelters()
    }

    /**
     * Check the cache status for shelters
     */
    override fun isCached(): Single<Boolean> {
        return sheltersCache.isCached()
    }

    override fun getShelterById(options: Map<String, String>): Single<ShelterEntity> {
        throw UnsupportedOperationException()
    }

}