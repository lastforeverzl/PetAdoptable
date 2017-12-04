package com.zackyzhang.petadoptable.data.source

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsDataStore
import com.zackyzhang.petadoptable.data.repository.PetsRemote
import io.reactivex.Completable
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

    override fun getPets(key: String, location: String, options: Map<String, String>):
            Single<List<PetEntity>> {
        return petsRemote.getPets(key, location, options)
    }

}