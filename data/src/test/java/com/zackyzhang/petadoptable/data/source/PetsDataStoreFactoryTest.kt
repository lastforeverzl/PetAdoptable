package com.zackyzhang.petadoptable.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.repository.PetsCache
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by lei on 12/3/17.
 */
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
    fun retrieveDataStoreWhenNotCachedReturnssRemoteDataStore() {
        stubPetsCacheIsCached(Single.just(false))
        val petsDataStore = petsDataStoreFactory.retrieveDataStore(false)
        assert(petsDataStore is PetsRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubPetsCacheIsCached(Single.just(true))
        stubPetsCacheIsExpired(true)
        val petsDataStore = petsDataStoreFactory.retrieveDataStore(true)
        assert(petsDataStore is PetsRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubPetsCacheIsCached(Single.just(true))
        stubPetsCacheIsExpired(false)
        val petsDataStore = petsDataStoreFactory.retrieveDataStore(true)
        assert(petsDataStore is PetsCacheDataStore)
    }

    @Test
    fun retrieveRemoteDataStoreReturnRemoteDataStore() {
        val petsDataStore = petsDataStoreFactory.retrieveRemoteDataStore()
        assert(petsDataStore is PetsRemoteDataStore)
    }

    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val petsDataStore = petsDataStoreFactory.retrieveCacheDataStore()
        assert(petsDataStore is PetsCacheDataStore)
    }

    private fun stubPetsCacheIsCached(single: Single<Boolean>) {
        whenever(petsCache.isCached())
                .thenReturn(single)
    }

    private fun stubPetsCacheIsExpired(isExpired: Boolean) {
        whenever(petsCache.isExpired())
                .thenReturn(isExpired)
    }
}