package com.zackyzhang.petadoptable.domain.repository

import com.zackyzhang.petadoptable.domain.model.Pet
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for how the Data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface PetsRepository {

    fun clearPets(): Completable

    fun savePets(pets: List<Pet>): Completable

    fun getPets(options: Map<String, String>): Flowable<List<Pet>>

    fun getFavoritePets(): Flowable<List<Pet>>

    fun saveToFavorite(pet: Pet): Completable

    fun removeFromFavorite(pet: Pet): Completable

    fun isFavoritePet(id: String): Single<Boolean>

    fun getPetById(options: Map<String, String>): Single<Pet>

    fun getShelterPets(options: Map<String, String>): Flowable<List<Pet>>

    fun getSearchPets(options: Map<String, String>): Flowable<List<Pet>>
}