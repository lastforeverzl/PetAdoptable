package com.zackyzhang.petadoptable.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsRemote
import com.zackyzhang.petadoptable.data.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.data.test.factory.PetFactory
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
        petsRemoteDataStore.savePets(PetFactory.makePetEntityList(2)).test()
    }

    @Test
    fun getPetsCompletes() {
        stubPetsRemoteGetPets(Flowable.just(PetFactory.makePetEntityList(2)))
        val testObserver = petsRemote.getPets(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    private fun stubPetsRemoteGetPets(flowable: Flowable<List<PetEntity>>) {
        whenever(petsRemote.getPets(mutableMapOf()))
                .thenReturn(flowable)
    }

}