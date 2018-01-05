package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.PetEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


/**
 * Interface defining methods for the data operations related to Pets.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface PetsDataStore {

    fun clearPets(): Completable

    fun savePets(pets: List<PetEntity>): Completable

    fun getPets(options: Map<String, String>): Flowable<List<PetEntity>>

    fun getFavoritePets(): Flowable<List<PetEntity>>

    fun saveToFavorite(pet: PetEntity): Completable

    fun removeFromFavorite(pet: PetEntity): Completable

    fun isFavoritePet(id: String): Single<Boolean>

    fun getPetById(options: Map<String, String>): Single<PetEntity>

    fun isCached(animal: String): Single<Boolean>

    fun getShelterPets(options: Map<String, String>): Flowable<List<PetEntity>>

}
