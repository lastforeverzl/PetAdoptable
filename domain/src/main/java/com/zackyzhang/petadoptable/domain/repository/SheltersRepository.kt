package com.zackyzhang.petadoptable.domain.repository

import com.zackyzhang.petadoptable.domain.model.Shelter
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Interface defining methods for how the Data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface SheltersRepository {

    fun clearShelters(): Completable

    fun saveShelters(pets: List<Shelter>): Completable

    fun getShelters(options: Map<String, String>): Flowable<List<Shelter>>
}
