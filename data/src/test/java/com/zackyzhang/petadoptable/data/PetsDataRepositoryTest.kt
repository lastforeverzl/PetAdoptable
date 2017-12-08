package com.zackyzhang.petadoptable.data

import com.nhaarman.mockito_kotlin.*
import com.zackyzhang.petadoptable.data.mapper.PetMapper
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsDataStore
import com.zackyzhang.petadoptable.data.source.PetsCacheDataStore
import com.zackyzhang.petadoptable.data.source.PetsDataStoreFactory
import com.zackyzhang.petadoptable.data.source.PetsRemoteDataStore
import com.zackyzhang.petadoptable.data.test.factory.PetFactory
import com.zackyzhang.petadoptable.domain.model.Pet
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PetsDataRepositoryTest {

    private lateinit var petsDataRepository: PetsDataRepository

    private lateinit var petsDataStoreFactory: PetsDataStoreFactory
    private lateinit var petMapper: PetMapper
    private lateinit var petsCacheDataStore: PetsCacheDataStore
    private lateinit var petsRemoteDataStore: PetsRemoteDataStore

    @Before
    fun setUp() {
        petMapper = mock()
        petsCacheDataStore = mock()
        petsRemoteDataStore = mock()
        petsDataStoreFactory = mock()
        petsDataRepository = PetsDataRepository(petsDataStoreFactory, petMapper)
        stubPetsDataStoreFactoryRetrieveCacheDataStore()
        stubPetsDataStoreFactoryRetrieveRemoteDataStore()
    }

    //<editor-fold desc="Clear Pets">
    @Test
    fun clearPetsCompletes() {
        stubPetsCacheClearPets(Completable.complete())
        val testObserver = petsCacheDataStore.clearPets().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearPetsCallsCacheDataStore() {
        stubPetsCacheClearPets(Completable.complete())
        petsCacheDataStore.clearPets().test()
        verify(petsCacheDataStore).clearPets()
    }

    @Test
    fun clearPetsNeverCallsRemoteDataStore() {
        stubPetsCacheClearPets(Completable.complete())
        petsCacheDataStore.clearPets().test()
        verify(petsRemoteDataStore, never()).clearPets()
    }
    //</editor-fold>

    //<editor-fold desc="Save Pets">
    @Test
    fun savePetsCompletes() {
        stubPetsCacheSavePets(Completable.complete())
        val testObserver = petsDataRepository.savePets(PetFactory.makePetList(2)).test()
        testObserver.assertComplete()
    }

    @Test
    fun savePetsCallsCacheDataStore() {
        stubPetsCacheSavePets(Completable.complete())
        petsDataRepository.savePets(PetFactory.makePetList(2)).test()
        verify(petsCacheDataStore).savePets(any())
    }

    @Test
    fun savePetsNeverCallsRemoteDataStore() {
        stubPetsCacheSavePets(Completable.complete())
        petsDataRepository.savePets(PetFactory.makePetList(2)).test()
        verify(petsRemoteDataStore, never()).savePets(any())
    }
    //</editor-fold>

    //<editor-fold desc="Get Pets">
    @Test
    fun getPetsCompletes() {
        stubPetsCacheDataStoreIsCached(Single.just(true))
        stubPetsDataStoreFactoryRetrieveDataStore(petsCacheDataStore)
        stubPetsCacheDataStoreGetPets(Flowable.just(PetFactory.makePetEntityList(2)))
        stubPetsCacheSavePets(Completable.complete())
        val testObserver = petsDataRepository.getPets(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPetsReturnsData() {
        stubPetsCacheDataStoreIsCached(Single.just(true))
        stubPetsDataStoreFactoryRetrieveDataStore(petsCacheDataStore)
        stubPetsCacheSavePets(Completable.complete())
        val pets = PetFactory.makePetList(2)
        val petEntities = PetFactory.makePetEntityList(2)
        pets.forEachIndexed { index, pet ->
            stubPetMapperMapFromEntity(petEntities[index], pet)
        }
        stubPetsCacheDataStoreGetPets(Flowable.just(petEntities))

        val testObserver = petsDataRepository.getPets(mutableMapOf()).test()
        testObserver.assertValue(pets)
    }

    @Test
    fun getPetsSavesPetsWhenFromCacheDataStore() {
        stubPetsDataStoreFactoryRetrieveDataStore(petsCacheDataStore)
        stubPetsCacheSavePets(Completable.complete())
        petsDataRepository.savePets(PetFactory.makePetList(2)).test()
        verify(petsCacheDataStore).savePets(any())
    }

    @Test
    fun getPetsNeverSavesPetsWhenFromRemoteDataStore() {
        stubPetsDataStoreFactoryRetrieveDataStore(petsRemoteDataStore)
        stubPetsCacheSavePets(Completable.complete())
        petsDataRepository.savePets(PetFactory.makePetList(2)).test()
        verify(petsRemoteDataStore, never()).savePets(any())
    }

    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubPetsDataStoreFactoryRetrieveCacheDataStore() {
        whenever(petsDataStoreFactory.retrieveCacheDataStore())
                .thenReturn(petsCacheDataStore)
    }

    private fun stubPetsDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(petsDataStoreFactory.retrieveRemoteDataStore())
                .thenReturn(petsRemoteDataStore)
    }

    private fun stubPetsCacheClearPets(completable: Completable) {
        whenever(petsCacheDataStore.clearPets())
                .thenReturn(completable)
    }

    private fun stubPetsCacheSavePets(completable: Completable) {
        whenever(petsCacheDataStore.savePets(any()))
                .thenReturn(completable)
    }

    private fun stubPetsCacheDataStoreIsCached(single: Single<Boolean>) {
        whenever(petsCacheDataStore.isCached())
                .thenReturn(single)
    }

    private fun stubPetsCacheDataStoreGetPets(flowable: Flowable<List<PetEntity>>) {
        whenever(petsCacheDataStore.getPets(mutableMapOf()))
                .thenReturn(flowable)
    }

    private fun stubPetsRemoteDataStoreGetPets(flowable: Flowable<List<PetEntity>>) {
        whenever(petsRemoteDataStore.getPets(mutableMapOf()))
                .thenReturn(flowable)
    }

    private fun stubPetMapperMapFromEntity(petEntity: PetEntity, pet: Pet) {
        whenever(petMapper.mapFromEntity(petEntity))
                .thenReturn(pet)
    }

    private fun stubPetsDataStoreFactoryRetrieveDataStore(dataStore: PetsDataStore) {
        whenever(petsDataStoreFactory.retrieveDataStore(any()))
                .thenReturn(dataStore)
    }
    //</editor-fold>
}