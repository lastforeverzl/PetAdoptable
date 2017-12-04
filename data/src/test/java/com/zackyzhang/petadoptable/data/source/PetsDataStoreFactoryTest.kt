package com.zackyzhang.petadoptable.data.source

import com.nhaarman.mockito_kotlin.mock
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

    private lateinit var petsRemoteDataStore: PetsRemoteDataStore

    @Before
    fun setUp() {
        petsRemoteDataStore = mock()
        petsDataStoreFactory = PetsDataStoreFactory(petsRemoteDataStore)
    }

    @Test
    fun retrieveRemoteDataStoreReturnRemoteDataStore() {
        val petsDataStore = petsDataStoreFactory.retrieveRemoteDataStore()
        assert(petsDataStore is PetsRemoteDataStore)
    }
}