package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface SheltersRemote {

    fun getShelters(options: Map<String, String>): Flowable<List<ShelterEntity>>

    fun getShelterById(options: Map<String, String>): Single<ShelterEntity>
}