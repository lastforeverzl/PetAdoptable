package com.zackyzhang.petadoptable.data.repository

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to Shelters.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface SheltersDataStore {

    fun clearShelters(): Completable

    fun saveShelters(shelters: List<ShelterEntity>): Completable

    fun getShelters(options: Map<String, String>): Flowable<List<ShelterEntity>>

    fun isCached(): Single<Boolean>

}