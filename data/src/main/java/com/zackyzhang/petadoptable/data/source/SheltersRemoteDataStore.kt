package com.zackyzhang.petadoptable.data.source

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.repository.SheltersDataStore
import com.zackyzhang.petadoptable.data.repository.SheltersRemote
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [SheltersDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class SheltersRemoteDataStore @Inject constructor(private val sheltersRemote: SheltersRemote) :
        SheltersDataStore {

    override fun clearShelters(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveShelters(shelters: List<ShelterEntity>): Completable {
        throw UnsupportedOperationException()
    }

    override fun getShelters(options: Map<String, String>): Flowable<List<ShelterEntity>> {
        return sheltersRemote.getShelters(options)
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

    override fun getShelterById(options: Map<String, String>): Single<ShelterEntity> {
        return sheltersRemote.getShelterById(options)
    }
}