package com.zackyzhang.petadoptable.data.source

import com.zackyzhang.petadoptable.data.repository.SheltersCache
import com.zackyzhang.petadoptable.data.repository.SheltersDataStore
import javax.inject.Inject

/**
 * Create an instance of a SheltersDataStore.
 */
open class SheltersDataStoreFactory @Inject constructor(
        private val sheltersCache: SheltersCache,
        private val sheltersCacheDataStore: SheltersCacheDataStore,
        private val sheltersRemoteDataStore: SheltersRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean, offset: String): SheltersDataStore {
        if (isCached && !sheltersCache.isExpired() && offset.toInt() == 0) {
            println("retrieveCacheDataStore")
            return retrieveCacheDataStore()
        }
        if (isCached && sheltersCache.isExpired()) {
            println("Cached but expired")
        }
        println("retrieveRemoteDataStore")
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of Cache Data Store
     */
    open fun retrieveCacheDataStore(): SheltersDataStore {
        return sheltersCacheDataStore
    }

    /**
     * Return an instance of Remote Data Store
     */
    open fun retrieveRemoteDataStore(): SheltersDataStore {
        return sheltersRemoteDataStore
    }
}

