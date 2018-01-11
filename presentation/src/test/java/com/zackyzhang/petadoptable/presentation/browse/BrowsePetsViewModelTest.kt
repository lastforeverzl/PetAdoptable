package com.zackyzhang.petadoptable.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPets
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.presentation.model.PetView
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
class BrowsePetsViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var getPets: GetPets
    @Mock lateinit var petMapper: PetMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Pet>>>

    private lateinit var petsViewModel: BrowsePetsViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor()
        getPets = mock()
        petMapper = mock()
        petsViewModel = BrowsePetsViewModel(getPets, petMapper)
    }

    @Test
    fun getPetsExecutesUseCase() {
        petsViewModel.getPetsLiveData()
        petsViewModel.fetchPets(randomUuid(), randomUuid(), randomInt(), randomUuid())
        verify(getPets, times(1)).execute(any(), any())
    }

    //<editor-fold desc="Success">
    @Test
    fun getPetsReturnsSuccess() {
        val list = PetsFactory.makePetList(2)
        val viewList = PetsFactory.makePetViewList(2)
        val key = randomUuid()
        val location = randomUuid()
        val animal = randomUuid()
        val offset = randomInt()

        stubPetMapperMapToView(viewList[0], list[0])
        stubPetMapperMapToView(viewList[1], list[1])

        petsViewModel.getPetsLiveData()
        petsViewModel.fetchPets(key, location, offset, animal)

//        verify(getPetsLiveData).execute(captor.capture(), eq(mapOf(Pair("key", key), Pair("location", location))))
        verify(getPets).execute(captor.capture(), any())
        captor.firstValue.onNext(list)

        assert(petsViewModel.getPetsLiveData().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun getPetsReturnsDataOnSuccess() {
        val list = PetsFactory.makePetList(2)
        val viewList = PetsFactory.makePetViewList(2)

        stubPetMapperMapToView(viewList[0], list[0])
        stubPetMapperMapToView(viewList[1], list[1])

        petsViewModel.getPetsLiveData()
        petsViewModel.fetchPets(randomUuid(), randomUuid(), randomInt(), randomUuid())

        verify(getPets).execute(captor.capture(), any())
        captor.firstValue.onNext(list)

        assert(petsViewModel.getPetsLiveData().value?.data == viewList)

    }

    @Test
    fun getPetsReturnsNoMessageOnSuccess() {
        val list = PetsFactory.makePetList(2)
        val viewList = PetsFactory.makePetViewList(2)

        stubPetMapperMapToView(viewList[0], list[0])
        stubPetMapperMapToView(viewList[1], list[1])

        petsViewModel.getPetsLiveData()
        petsViewModel.fetchPets(randomUuid(), randomUuid(), randomInt(), randomUuid())

        verify(getPets).execute(captor.capture(), any())
        captor.firstValue.onNext(list)

        assert(petsViewModel.getPetsLiveData().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getPetsReturnsError() {
        petsViewModel.getPetsLiveData()
        petsViewModel.fetchPets(randomUuid(), randomUuid(), randomInt(), randomUuid())

        verify(getPets).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assert(petsViewModel.getPetsLiveData().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getPetsFailsAndContainsNoData() {
        petsViewModel.getPetsLiveData()
        petsViewModel.fetchPets(randomUuid(), randomUuid(), randomInt(), randomUuid())

        verify(getPets).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assert(petsViewModel.getPetsLiveData().value?.data == null)
    }

    @Test
    fun getPetsFailsAndContainsMessage() {
        val errorMessage = randomUuid()
        petsViewModel.getPetsLiveData()
        petsViewModel.fetchPets(randomUuid(), randomUuid(), randomInt(), randomUuid())

        verify(getPets).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(petsViewModel.getPetsLiveData().value?.message == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getPetsReturnsLoading() {
        petsViewModel.getPetsLiveData()

        assert(petsViewModel.getPetsLiveData().value?.status == ResourceState.LOADING)
    }

    @Test
    fun getPetsContainsNoDataWhenLoading() {
        petsViewModel.getPetsLiveData()

        assert(petsViewModel.getPetsLiveData().value?.data == null)
    }

    @Test
    fun getPetsContainsNoMessageWhenLoading() {
        petsViewModel.getPetsLiveData()

        assert(petsViewModel.getPetsLiveData().value?.message == null)
    }
    //</editor-fold>

    private fun stubPetMapperMapToView(petView: PetView, pet: Pet) {
        whenever(petMapper.mapToView(pet))
                .thenReturn(petView)
    }
}