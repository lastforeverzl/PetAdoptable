package com.zackyzhang.petadoptable.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelterPets
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory.Factory.randomInt
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.presentation.test.factory.PetsFactory
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

@RunWith(JUnit4::class)
class BrowseShelterPetsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var getShelterPets: GetShelterPets
    @Mock lateinit var petMapper: PetMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Pet>>>

    private lateinit var shelterPetsViewModel: BrowseShelterPetsViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor()
        getShelterPets = mock()
        petMapper = mock()
        shelterPetsViewModel = BrowseShelterPetsViewModel(getShelterPets, petMapper)
    }

    @Test
    fun getPetsExecutesUseCase() {
        shelterPetsViewModel.getPetsLiveData()
        shelterPetsViewModel.fetchPets(randomUuid(), randomUuid(), randomInt())
        verify(getShelterPets, times(1)).execute(any(), any())
    }

    //<editor-fold desc="Success">
    @Test
    fun getPetsReturnsSuccess() {
        val list = PetsFactory.makePetList(2)
        val viewList = PetsFactory.makePetViewList(2)
        val key = DataFactory.randomUuid()
        val location = DataFactory.randomUuid()
        val animal = DataFactory.randomUuid()
        val offset = DataFactory.randomInt()

        stubPetMapperMapToView(viewList[0], list[0])
        stubPetMapperMapToView(viewList[1], list[1])

        shelterPetsViewModel.getPetsLiveData()
        shelterPetsViewModel.fetchPets(key, location, offset)

//        verify(getPetsLiveData).execute(captor.capture(), eq(mapOf(Pair("key", key), Pair("location", location))))
        verify(getShelterPets).execute(captor.capture(), any())
        captor.firstValue.onNext(list)

        assert(shelterPetsViewModel.getPetsLiveData().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun getPetsReturnsDataOnSuccess() {
        val list = PetsFactory.makePetList(2)
        val viewList = PetsFactory.makePetViewList(2)

        stubPetMapperMapToView(viewList[0], list[0])
        stubPetMapperMapToView(viewList[1], list[1])

        shelterPetsViewModel.getPetsLiveData()
        shelterPetsViewModel.fetchPets(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomInt())

        verify(getShelterPets).execute(captor.capture(), any())
        captor.firstValue.onNext(list)

        assert(shelterPetsViewModel.getPetsLiveData().value?.data == viewList)

    }

    @Test
    fun getPetsReturnsNoMessageOnSuccess() {
        val list = PetsFactory.makePetList(2)
        val viewList = PetsFactory.makePetViewList(2)

        stubPetMapperMapToView(viewList[0], list[0])
        stubPetMapperMapToView(viewList[1], list[1])

        shelterPetsViewModel.getPetsLiveData()
        shelterPetsViewModel.fetchPets(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomInt())

        verify(getShelterPets).execute(captor.capture(), any())
        captor.firstValue.onNext(list)

        assert(shelterPetsViewModel.getPetsLiveData().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getPetsReturnsError() {
        shelterPetsViewModel.getPetsLiveData()
        shelterPetsViewModel.fetchPets(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomInt())

        verify(getShelterPets).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assert(shelterPetsViewModel.getPetsLiveData().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getPetsFailsAndContainsNoData() {
        shelterPetsViewModel.getPetsLiveData()
        shelterPetsViewModel.fetchPets(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomInt())

        verify(getShelterPets).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assert(shelterPetsViewModel.getPetsLiveData().value?.data == null)
    }

    @Test
    fun getPetsFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        shelterPetsViewModel.getPetsLiveData()
        shelterPetsViewModel.fetchPets(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomInt())

        verify(getShelterPets).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(shelterPetsViewModel.getPetsLiveData().value?.message == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getPetsReturnsLoading() {
        shelterPetsViewModel.getPetsLiveData()

        assert(shelterPetsViewModel.getPetsLiveData().value?.status == ResourceState.LOADING)
    }

    @Test
    fun getPetsContainsNoDataWhenLoading() {
        shelterPetsViewModel.getPetsLiveData()

        assert(shelterPetsViewModel.getPetsLiveData().value?.data == null)
    }

    @Test
    fun getPetsContainsNoMessageWhenLoading() {
        shelterPetsViewModel.getPetsLiveData()

        assert(shelterPetsViewModel.getPetsLiveData().value?.message == null)
    }
    //</editor-fold>

    private fun stubPetMapperMapToView(petView: PetView, pet: Pet) {
        whenever(petMapper.mapToView(pet))
                .thenReturn(petView)
    }
}