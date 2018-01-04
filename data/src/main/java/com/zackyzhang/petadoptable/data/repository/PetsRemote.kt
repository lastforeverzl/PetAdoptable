package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.PetEntity
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the remote of Pets. This is to be implemented by the
 * remote layer, using this interface as a way of communicating.
 */
interface PetsRemote {

    fun getPets(options: Map<String, String>): Flowable<List<PetEntity>>

    /**
     * Retrieve a pet by id
     */
    fun getPetById(options: Map<String, String>): Single<PetEntity>
}