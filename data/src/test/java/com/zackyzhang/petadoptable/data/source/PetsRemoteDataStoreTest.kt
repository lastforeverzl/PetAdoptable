package com.zackyzhang.petadoptable.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsRemote
import com.zackyzhang.petadoptable.data.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.data.test.factory.PetsFactory
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by lei on 12/3/17.
 */
@RunWith(JUnit4::class)
class PetsRemoteDataStoreTest {

    private lateinit var petsRemoteDataStore: PetsRemoteDataStore

    private lateinit var petsRemote: PetsRemote

    private lateinit var key: String
    private lateinit var location: String

    @Before
    fun setUp() {
        key = randomUuid()
        location = randomUuid()
        petsRemote = mock()
        petsRemoteDataStore = PetsRemoteDataStore(petsRemote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearPetsThrowsException() {
        petsRemoteDataStore.clearPets().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun savePetsThrowsException() {
        petsRemoteDataStore.savePets(PetsFactory.makePetEntityList(2)).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getFavoritePetsThrowsException() {
        petsRemoteDataStore.getFavoritePets().test()
    }

    @Test
    fun getPetsCompletes() {
        stubPetsRemoteGetPets(Flowable.just(PetsFactory.makePetEntityList(2)))
        val testObserver = petsRemote.getPets(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getShelterPetsCompletes() {
        stubPetsRemoteGetShelterPets(Flowable.just(PetsFactory.makePetEntityList(2)))
        val testObserver = petsRemote.getShelterPets(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    private fun stubPetsRemoteGetPets(flowable: Flowable<List<PetEntity>>) {
        whenever(petsRemote.getPets(mutableMapOf()))
                .thenReturn(flowable)
    }

    private fun stubPetsRemoteGetShelterPets(flowable: Flowable<List<PetEntity>>) {
        whenever(petsRemote.getShelterPets(mutableMapOf()))
                .thenReturn(flowable)
    }

}