package com.zackyzhang.petadoptable.data.source

import com.zackyzhang.petadoptable.data.repository.PetsCache
import com.zackyzhang.petadoptable.data.repository.PetsDataStore
import javax.inject.Inject

/**
 * Create an instance of a PetsDataStore
 */
open class PetsDataStoreFactory @Inject constructor(
        private val petsCache: PetsCache,
        private val petsCacheDataStore: PetsCacheDataStore,
        private val petsRemoteDataStore: PetsRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(offset: String): PetsDataStore {
        if (petsCache.isCached() && !petsCache.isExpired() && offset.toInt() == 0) {
            println("retrieveCacheDataStore")
            return retrieveCacheDataStore()
        }
        if (petsCache.isCached() && petsCache.isExpired()) {
            println("Cached but expired")
        }
        println("retrieveRemoteDataStore")
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of Cache Data Store
     */
    open fun retrieveCacheDataStore(): PetsDataStore {
        return petsCacheDataStore
    }

    /**
     * Return an instance of Remote Data Store
     */
    open fun retrieveRemoteDataStore(): PetsDataStore {
        return petsRemoteDataStore
    }
}
