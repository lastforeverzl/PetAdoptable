package com.zackyzhang.petadoptable.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.repository.PetsCache
import io.reactivex.Single
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PetsDataStoreFactoryTest {

    private lateinit var petsDataStoreFactory: PetsDataStoreFactory

    private lateinit var petsCache: PetsCache
    private lateinit var petsCacheDataStore: PetsCacheDataStore
    private lateinit var petsRemoteDataStore: PetsRemoteDataStore

    @Before
    fun setUp() {
        petsCache = mock()
        petsCacheDataStore = mock()
        petsRemoteDataStore = mock()
        petsDataStoreFactory = PetsDataStoreFactory(petsCache, petsCacheDataStore, petsRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubPetsCacheIsCached(Single.just(false))
        val petsDataStore = petsDataStoreFactory.retrieveDataStore(false, "0")
        assertThat(petsDataStore, instanceOf(PetsRemoteDataStore::class.java))
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubPetsCacheIsCached(Single.just(true))
        stubPetsCacheIsExpired(true)
        val petsDataStore = petsDataStoreFactory.retrieveDataStore(true,"25")
        assertThat(petsDataStore, instanceOf(PetsRemoteDataStore::class.java))
    }

    @Test
    fun retrieveDataStoreWhenCacheExpireOffsetReturnsRemoteDataStore() {
        stubPetsCacheIsCached(Single.just(true))
        stubPetsCacheIsExpired(false)
        val petsDataStore = petsDataStoreFactory.retrieveDataStore(true, "25")
        assertThat(petsDataStore, instanceOf(PetsRemoteDataStore::class.java))
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubPetsCacheIsCached(Single.just(true))
        stubPetsCacheIsExpired(false)
        val petsDataStore = petsDataStoreFactory.retrieveDataStore(true, "0")
        assertThat(petsDataStore, instanceOf(PetsCacheDataStore::class.java))
    }

    @Test
    fun retrieveRemoteDataStoreReturnRemoteDataStore() {
        val petsDataStore = petsDataStoreFactory.retrieveRemoteDataStore()
        assertThat(petsDataStore, instanceOf(PetsRemoteDataStore::class.java))
    }

    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val petsDataStore = petsDataStoreFactory.retrieveCacheDataStore()
        assertThat(petsDataStore, instanceOf(PetsCacheDataStore::class.java))
    }

    private fun stubPetsCacheIsCached(isCached: Single<Boolean>) {
        whenever(petsCache.isCached(any()))
                .thenReturn(isCached)
    }

    private fun stubPetsCacheIsExpired(isExpired: Boolean) {
        whenever(petsCache.isExpired())
                .thenReturn(isExpired)
    }
}