package com.zackyzhang.petadoptable.cache

import com.zackyzhang.petadoptable.cache.db.PetAdoptableDatabase
import com.zackyzhang.petadoptable.cache.db.entity.ShelterDbEntity
import com.zackyzhang.petadoptable.cache.mapper.ShelterEntityMapper
import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.repository.SheltersCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving Pets instances. This class implements the
 * [SheltersCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class SheltersCacheImpl @Inject constructor(private val petAdoptableDatabase: PetAdoptableDatabase,
                                            private val entityMapper: ShelterEntityMapper,
                                            private val preferencesHelper: PreferencesHelper) :
        SheltersCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    override fun clearShelters(): Completable {
        return Completable.defer {
            petAdoptableDatabase.getShelterDao().clearShelters()
            println("SheltersCacheImpl clearShelters")
            Completable.complete()
        }
    }

    override fun saveShelters(shelters: List<ShelterEntity>): Completable {
        return Completable.defer {
            shelters.forEach {
                saveShelter(entityMapper.mapToCached(it))
            }
            Completable.complete()
        }
    }

    private fun saveShelter(shelter: ShelterDbEntity): Long {
        return petAdoptableDatabase.getShelterDao().insertShelter(shelter)
    }

    override fun getShelters(): Flowable<List<ShelterEntity>> {
        return Flowable.defer {
            Flowable.just(petAdoptableDatabase.getShelterDao().getAllShelters())
        }.map {
            it.map {
                entityMapper.mapFromCached(it)
            }
        }
    }

    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(petAdoptableDatabase.getShelterDao().getAllShelters().isNotEmpty())
        }
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.sheltersLastCacheTime = lastCache
    }

    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.sheltersLastCacheTime
    }

}
