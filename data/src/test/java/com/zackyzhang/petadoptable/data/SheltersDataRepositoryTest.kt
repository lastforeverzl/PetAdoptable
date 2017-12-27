package com.zackyzhang.shelteradoptable.data

import com.nhaarman.mockito_kotlin.*
import com.zackyzhang.petadoptable.data.SheltersDataRepository
import com.zackyzhang.petadoptable.data.mapper.ShelterMapper
import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.repository.SheltersDataStore
import com.zackyzhang.petadoptable.data.source.SheltersCacheDataStore
import com.zackyzhang.petadoptable.data.source.SheltersDataStoreFactory
import com.zackyzhang.petadoptable.data.source.SheltersRemoteDataStore
import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.shelteradoptable.data.test.factory.SheltersFactory
import io.reactivex.Completable
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
class SheltersDataRepositoryTest {

    private lateinit var sheltersDataRepository: SheltersDataRepository

    private lateinit var sheltersDataStoreFactory: SheltersDataStoreFactory
    private lateinit var shelterMapper: ShelterMapper
    private lateinit var sheltersCacheDataStore: SheltersCacheDataStore
    private lateinit var sheltersRemoteDataStore: SheltersRemoteDataStore

    @Before
    fun setUp() {
        shelterMapper = mock()
        sheltersCacheDataStore = mock()
        sheltersRemoteDataStore = mock()
        sheltersDataStoreFactory = mock()
        sheltersDataRepository = SheltersDataRepository(sheltersDataStoreFactory, shelterMapper)
        stubSheltersDataStoreFactoryRetrieveCacheDataStore()
        stubSheltersDataStoreFactoryRetrieveRemoteDataStore()
    }

    //<editor-fold desc="Clear Shelters">
    @Test
    fun clearSheltersCompletes() {
        stubSheltersCacheClearShelters(Completable.complete())
        val testObserver = sheltersCacheDataStore.clearShelters().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearSheltersCallsCacheDataStore() {
        stubSheltersCacheClearShelters(Completable.complete())
        sheltersCacheDataStore.clearShelters().test()
        verify(sheltersCacheDataStore).clearShelters()
    }

    @Test
    fun clearSheltersNeverCallsRemoteDataStore() {
        stubSheltersCacheClearShelters(Completable.complete())
        sheltersCacheDataStore.clearShelters().test()
        verify(sheltersRemoteDataStore, never()).clearShelters()
    }
    //</editor-fold>

    //<editor-fold desc="Save Shelters">
    @Test
    fun saveSheltersCompletes() {
        stubSheltersCacheSaveShelters(Completable.complete())
        val testObserver = sheltersDataRepository.saveShelters(SheltersFactory.makeShelterList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveSheltersCallsCacheDataStore() {
        stubSheltersCacheSaveShelters(Completable.complete())
        sheltersDataRepository.saveShelters(SheltersFactory.makeShelterList(2)).test()
        verify(sheltersCacheDataStore).saveShelters(any())
    }

    @Test
    fun saveSheltersNeverCallsRemoteDataStore() {
        stubSheltersCacheSaveShelters(Completable.complete())
        sheltersDataRepository.saveShelters(SheltersFactory.makeShelterList(2)).test()
        verify(sheltersRemoteDataStore, never()).saveShelters(any())
    }
    //</editor-fold>

    //<editor-fold desc="Get Shelters">
    @Test
    fun getSheltersCompletes() {
        stubSheltersCacheDataStoreIsCached(Single.just(true))
        stubSheltersDataStoreFactoryRetrieveDataStore(sheltersCacheDataStore)
        stubSheltersCacheDataStoreGetShelters(Flowable.just(SheltersFactory.makeShelterEntityList(2)))
        stubSheltersCacheSaveShelters(Completable.complete())
        val testObserver = sheltersDataRepository.getShelters(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSheltersReturnsData() {
        stubSheltersCacheDataStoreIsCached(Single.just(true))
        stubSheltersDataStoreFactoryRetrieveDataStore(sheltersCacheDataStore)
        stubSheltersCacheSaveShelters(Completable.complete())
        val shelters = SheltersFactory.makeShelterList(2)
        val shelterEntities = SheltersFactory.makeShelterEntityList(2)
        shelters.forEachIndexed { index, shelter ->
            stubShelterMapperMapFromEntity(shelterEntities[index], shelter)
        }
        stubSheltersCacheDataStoreGetShelters(Flowable.just(shelterEntities))

        val testObserver = sheltersDataRepository.getShelters(mutableMapOf()).test()
        testObserver.assertValue(shelters)
    }

    @Test
    fun getSheltersSavesSheltersWhenFromCacheDataStore() {
        stubSheltersDataStoreFactoryRetrieveDataStore(sheltersCacheDataStore)
        stubSheltersCacheSaveShelters(Completable.complete())
        sheltersDataRepository.saveShelters(SheltersFactory.makeShelterList(2)).test()
        verify(sheltersCacheDataStore).saveShelters(any())
    }

    @Test
    fun getSheltersNeverSavesSheltersWhenFromRemoteDataStore() {
        stubSheltersDataStoreFactoryRetrieveDataStore(sheltersRemoteDataStore)
        stubSheltersCacheSaveShelters(Completable.complete())
        sheltersDataRepository.saveShelters(SheltersFactory.makeShelterList(2)).test()
        verify(sheltersRemoteDataStore, never()).saveShelters(any())
    }

    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubSheltersDataStoreFactoryRetrieveCacheDataStore() {
        whenever(sheltersDataStoreFactory.retrieveCacheDataStore())
                .thenReturn(sheltersCacheDataStore)
    }

    private fun stubSheltersDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(sheltersDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(sheltersRemoteDataStore)
    }

    private fun stubSheltersCacheClearShelters(completable: Completable) {
        whenever(sheltersCacheDataStore.clearShelters())
                .thenReturn(completable)
    }

    private fun stubSheltersCacheSaveShelters(completable: Completable) {
        whenever(sheltersCacheDataStore.saveShelters(any()))
                .thenReturn(completable)
    }

    private fun stubSheltersCacheDataStoreIsCached(single: Single<Boolean>) {
        whenever(sheltersCacheDataStore.isCached())
                .thenReturn(single)
    }

    private fun stubSheltersCacheDataStoreGetShelters(flowable: Flowable<List<ShelterEntity>>) {
        whenever(sheltersCacheDataStore.getShelters(mutableMapOf()))
                .thenReturn(flowable)
    }

    private fun stubSheltersRemoteDataStoreGetShelters(flowable: Flowable<List<ShelterEntity>>) {
        whenever(sheltersRemoteDataStore.getShelters(mutableMapOf()))
                .thenReturn(flowable)
    }

    private fun stubShelterMapperMapFromEntity(shelterEntity: ShelterEntity, shelter: Shelter) {
        whenever(shelterMapper.mapFromEntity(shelterEntity))
                .thenReturn(shelter)
    }

    private fun stubSheltersDataStoreFactoryRetrieveDataStore(dataStore: SheltersDataStore) {
        whenever(sheltersDataStoreFactory.retrieveDataStore(any(), any()))
                .thenReturn(dataStore)
    }
    //</editor-fold>
}