package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import io.reactivex.Flowable

/**
 * Created by lei on 12/21/17.
 */
interface SheltersRemote {

    fun getShelters(options: Map<String, String>): Flowable<List<ShelterEntity>>

}