package com.zackyzhang.petadoptable.domain.repository

import com.zackyzhang.petadoptable.domain.model.Pet
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Interface defining methods for how the Data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface PetsRepository {

    fun clearPets(): Completable

    fun savePets(pets: List<Pet>): Completable

    fun getPets(key: String, location: String, options: Map<String, String>): Flowable<List<Pet>>
}