package com.zackyzhang.petadoptable.data

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.mapper.PetMapper
import com.zackyzhang.petadoptable.data.repository.PetsDataStore
import com.zackyzhang.petadoptable.data.source.PetsDataStoreFactory
import com.zackyzhang.petadoptable.data.source.PetsRemoteDataStore
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

// TODO: PetsDataRepository test need to be implemented after cache layer finished.
@RunWith(JUnit4::class)
class PetsDataRepositoryTest {

    private lateinit var petsDataRepository: PetsDataRepository

    private lateinit var petsDataStoreFactory: PetsDataStoreFactory
    private lateinit var petMapper: PetMapper
    private lateinit var petsRemoteDataStore: PetsRemoteDataStore

    @Before
    fun setUp() {
        petMapper = mock()
        petsRemoteDataStore = mock()
        petsDataStoreFactory = mock()
        petsDataRepository = PetsDataRepository(petsDataStoreFactory, petMapper)
        stubPetsDataStoreFactoryRetrieveRemoteDataStore()
    }

    @Test
    fun clearPetsCompletes() {

    }

    @Test
    fun getPetsNeverSavesPetsWhenFromRemoteDataStore() {
        stubPetsDataStoreFactoryRetrieveDataStore(petsRemoteDataStore)

    }

    private fun stubPetsDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(petsDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(petsRemoteDataStore)
    }

    private fun stubPetsDataStoreFactoryRetrieveDataStore(dataStore: PetsDataStore) {
        whenever(petsDataStoreFactory.retriveDataStore())
                .thenReturn(dataStore)
    }
}