package com.zackyzhang.petadoptable.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.repository.SheltersRemote
import com.zackyzhang.petadoptable.data.test.factory.DataFactory
import com.zackyzhang.shelteradoptable.data.test.factory.SheltersFactory
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by lei on 12/22/17.
 */
@RunWith(JUnit4::class)
class SheltersRemoteDataStoreTest {

    private lateinit var sheltersRemoteDataStore: SheltersRemoteDataStore

    private lateinit var sheltersRemote: SheltersRemote

    private lateinit var key: String
    private lateinit var location: String

    @Before
    fun setUp() {
        key = DataFactory.randomUuid()
        location = DataFactory.randomUuid()
        sheltersRemote = mock()
        sheltersRemoteDataStore = SheltersRemoteDataStore(sheltersRemote)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearSheltersThrowsException() {
        sheltersRemoteDataStore.clearShelters().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveSheltersThrowsException() {
        sheltersRemoteDataStore.saveShelters(SheltersFactory.makeShelterEntityList(2)).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun isCachedThrowsException() {
        sheltersRemoteDataStore.clearShelters().test()
    }

    @Test
    fun getSheltersCompletes() {
        stubSheltersRemoteGetPets(Flowable.just(SheltersFactory.makeShelterEntityList(2)))
        val testObserver = sheltersRemote.getShelters(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getShelterByIdCompletes() {
        stubSheltersRemoteGetShelterById(Single.just(SheltersFactory.makeShelterEntity()))
        val testObserver = sheltersRemote.getShelterById(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    private fun stubSheltersRemoteGetPets(flowable: Flowable<List<ShelterEntity>>) {
        whenever(sheltersRemote.getShelters(mutableMapOf()))
                .thenReturn(flowable)
    }

    private fun stubSheltersRemoteGetShelterById(single: Single<ShelterEntity>) {
        whenever(sheltersRemote.getShelterById(mutableMapOf()))
                .thenReturn(single)
    }

}