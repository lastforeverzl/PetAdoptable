package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.PetEntity
import io.reactivex.Flowable

/**
 * Interface defining methods for the remote of Pets. This is to be implemented by the
 * remote layer, using this interface as a way of communicating.
 */
interface PetsRemote {

    fun getPets(options: Map<String, String>): Flowable<List<PetEntity>>
}