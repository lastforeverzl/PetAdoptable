package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.PetEntity
import io.reactivex.Completable
import io.reactivex.Single


/**
 * Interface defining methods for the data operations related to Bufferroos.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface PetsDataStore {

    fun clearPets(): Completable

    fun savePets(pets: List<PetEntity>): Completable

    fun getPets(key: String, location: String, options: Map<String, String>): Single<List<PetEntity>>
}
