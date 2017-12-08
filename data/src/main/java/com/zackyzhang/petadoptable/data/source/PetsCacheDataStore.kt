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
    }

    /**
     * Save a given [List] of [PetEntity] instances to the cache
     */
    override fun savePets(pets: List<PetEntity>): Completable {
        return petsCache.savePets(pets)
                .doOnComplete {
                    petsCache.setLastCacheTime(System.currentTimeMillis())
                }
    }

    /**
     * Retrieve a list of [PetEntity] instance from the cache
     */
    override fun getPets(options: Map<String, String>):
            Flowable<List<PetEntity>> {
        return petsCache.getPets()
    }

    override fun isCached(): Single<Boolean> {
        return petsCache.isCached()
    }
}