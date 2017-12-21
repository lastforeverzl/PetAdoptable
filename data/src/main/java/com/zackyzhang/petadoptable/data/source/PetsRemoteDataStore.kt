package com.zackyzhang.petadoptable.data.source

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsDataStore
import com.zackyzhang.petadoptable.data.repository.PetsRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementaion of the [PetsDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class PetsRemoteDataStore @Inject constructor(private val petsRemote: PetsRemote) :
        PetsDataStore {

    override fun clearPets(): Completable {
        throw UnsupportedOperationException()
    }

    override fun savePets(pets: List<PetEntity>): Completable {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a list of [PetEntity] instances from the API
     */
    override fun getPets(options: Map<String, String>):
            Flowable<List<PetEntity>> {
        return petsRemote.getPets(options)
    }

    override fun isCached(animal: String): Single<Boolean> {
        throw UnsupportedOperationException()
    }
}