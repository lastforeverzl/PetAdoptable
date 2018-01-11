package com.zackyzhang.shelteradoptable.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.repository.SheltersCache
import com.zackyzhang.petadoptable.data.source.SheltersCacheDataStore
import com.zackyzhang.petadoptable.data.source.SheltersDataStoreFactory
import com.zackyzhang.petadoptable.data.source.SheltersRemoteDataStore
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SheltersDataStoreFactoryTest {

    private lateinit var sheltersDataStoreFactory: SheltersDataStoreFactory

    private lateinit var sheltersCache: SheltersCache
    private lateinit var sheltersCacheDataStore: SheltersCacheDataStore
    private lateinit var sheltersRemoteDataStore: SheltersRemoteDataStore

    @Before
    fun setUp() {
        sheltersCache = mock()
        sheltersCacheDataStore = mock()
        sheltersRemoteDataStore = mock()
        sheltersDataStoreFactory = SheltersDataStoreFactory(sheltersCache, sheltersCacheDataStore, sheltersRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubSheltersCacheIsCached(Single.just(false))
        val sheltersDataStore = sheltersDataStoreFactory.retrieveDataStore(false, "0")
        Assert.assertThat(sheltersDataStore, CoreMatchers.instanceOf(SheltersRemoteDataStore::class.java))
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubSheltersCacheIsCached(Single.just(true))
        stubSheltersCacheIsExpired(true)
        val sheltersDataStore = sheltersDataStoreFactory.retrieveDataStore(true,"25")
        Assert.assertThat(sheltersDataStore, CoreMatchers.instanceOf(SheltersRemoteDataStore::class.java))
    }

    @Test
    fun retrieveDataStoreWhenCacheExpireOffsetReturnsRemoteDataStore() {
        stubSheltersCacheIsCached(Single.just(true))
        stubSheltersCacheIsExpired(false)
        val sheltersDataStore = sheltersDataStoreFactory.retrieveDataStore(true, "25")
        Assert.assertThat(sheltersDataStore, CoreMatchers.instanceOf(SheltersRemoteDataStore::class.java))
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubSheltersCacheIsCached(Single.just(true))
        stubSheltersCacheIsExpired(false)
        val sheltersDataStore = sheltersDataStoreFactory.retrieveDataStore(true, "0")
        Assert.assertThat(sheltersDataStore, CoreMatchers.instanceOf(SheltersCacheDataStore::class.java))
    }

    @Test
    fun retrieveRemoteDataStoreReturnRemoteDataStore() {
        val sheltersDataStore = sheltersDataStoreFactory.retrieveRemoteDataStore()
        Assert.assertThat(sheltersDataStore, CoreMatchers.instanceOf(SheltersRemoteDataStore::class.java))
    }

    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val sheltersDataStore = sheltersDataStoreFactory.retrieveCacheDataStore()
        Assert.assertThat(sheltersDataStore, CoreMatchers.instanceOf(SheltersCacheDataStore::class.java))
    }

    private fun stubSheltersCacheIsCached(isCached: Single<Boolean>) {
        whenever(sheltersCache.isCached())
                .thenReturn(isCached)
    }

    private fun stubSheltersCacheIsExpired(isExpired: Boolean) {
        whenever(sheltersCache.isExpired())
                .thenReturn(isExpired)
    }
}