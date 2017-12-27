package com.zackyzhang.shelteradoptable.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.repository.SheltersCache
import com.zackyzhang.petadoptable.data.source.SheltersCacheDataStore
import com.zackyzhang.petadoptable.data.test.factory.DataFactory
import com.zackyzhang.shelteradoptable.data.test.factory.SheltersFactory
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by lei on 12/22/17.
 */
@RunWith(JUnit4::class)
class SheltersCacheDataStoreTest {

    private lateinit var sheltersCacheDataStore: SheltersCacheDataStore

    private lateinit var sheltersCache: SheltersCache

    private lateinit var key: String
    private lateinit var location: String

    @Before
    fun setUp() {
        key = DataFactory.randomUuid()
        location = DataFactory.randomUuid()
        sheltersCache = mock()
        sheltersCacheDataStore = SheltersCacheDataStore(sheltersCache)
    }

    @Test
    fun clearSheltersCompletes() {
        stubSheltersCacheClearShelters(Completable.complete())
        val testObserver = sheltersCacheDataStore.clearShelters().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveSheltersCompletes() {
        stubSheltersCacheSaveShelters(Completable.complete())
        val testObserver = sheltersCacheDataStore.saveShelters(SheltersFactory.makeShelterEntityList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSheltersCompletes() {
        stubSheltersCacheGetShelters(Flowable.just(SheltersFactory.makeShelterEntityList(2)))
        val testObserver = sheltersCacheDataStore.getShelters(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    private fun stubSheltersCacheClearShelters(completable: Completable) {
        whenever(sheltersCache.clearShelters())
                .thenReturn(completable)
    }

    private fun stubSheltersCacheSaveShelters(completable: Completable) {
        whenever(sheltersCache.saveShelters(any()))
                .thenReturn(completable)
    }

    private fun stubSheltersCacheGetShelters(flowable: Flowable<List<ShelterEntity>>) {
        whenever(sheltersCache.getShelters())
                .thenReturn(flowable)
    }
}