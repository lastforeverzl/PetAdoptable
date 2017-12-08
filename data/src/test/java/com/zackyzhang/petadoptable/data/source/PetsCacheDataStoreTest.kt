package com.zackyzhang.petadoptable.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsCache
import com.zackyzhang.petadoptable.data.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.data.test.factory.PetFactory
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by lei on 12/6/17.
 */
@RunWith(JUnit4::class)
class PetsCacheDataStoreTest {

    private lateinit var petsCacheDataStore: PetsCacheDataStore

    private lateinit var petsCache: PetsCache

    private lateinit var key: String
    private lateinit var location: String

    @Before
    fun setUp() {
        key = randomUuid()
        location = randomUuid()
        petsCache = mock()
        petsCacheDataStore = PetsCacheDataStore(petsCache)
    }

    @Test
    fun clearPetsCompletes() {
        stubPetsCacheClearPets(Completable.complete())
        val testObserver = petsCacheDataStore.clearPets().test()
        testObserver.assertComplete()
    }

    @Test
    fun savePetsCompletes() {
        stubPetsCacheSavePets(Completable.complete())
        val testObserver = petsCacheDataStore.savePets(PetFactory.makePetEntityList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPetsCompletes() {
        stubPetsCacheGetPets(Flowable.just(PetFactory.makePetEntityList(2)))
        val testObserver = petsCacheDataStore.getPets(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    private fun stubPetsCacheClearPets(completable: Completable) {
        whenever(petsCache.clearPets())
                .thenReturn(completable)
    }

    private fun stubPetsCacheSavePets(completable: Completable) {
        whenever(petsCache.savePets(any()))
                .thenReturn(completable)
    }

    private fun stubPetsCacheGetPets(flowable: Flowable<List<PetEntity>>) {
        whenever(petsCache.getPets())
                .thenReturn(flowable)
    }
}