package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.PetEntity
import io.reactivex.Single

/**
 * Created by lei on 12/1/17.
 */
interface PetsRemote {

    fun getPets(key: String, location: String, options: Map<String, String>): Single<List<PetEntity>>
}