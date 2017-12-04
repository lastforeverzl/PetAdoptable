package com.zackyzhang.petadoptable.data.source

import com.zackyzhang.petadoptable.data.repository.PetsDataStore
import javax.inject.Inject

/**
 * Create an instance of a PetsDataStore
 */
open class PetsDataStoreFactory @Inject constructor(
        private val petsRemoteDataStore: PetsRemoteDataStore) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retriveDataStore(): PetsDataStore {
        TODO("need implemented cache")
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of Remote Data Store
     */
    open fun retrieveRemoteDataStore(): PetsDataStore {
        return petsRemoteDataStore
    }
}
