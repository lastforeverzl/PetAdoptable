package com.zackyzhang.petadoptable.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetById
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory
import com.zackyzhang.petadoptable.presentation.test.factory.PetsFactory
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

/**
 * Created by lei on 12/29/17.
 */
@RunWith(JUnit4::class)
class BrowsePetViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var getPetById: GetPetById
    @Mock lateinit var petMapper: PetMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<Pet>>

    private lateinit var petViewModel: BrowsePetViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor()
        getPetById = mock()
        petMapper = mock()
        petViewModel = BrowsePetViewModel(getPetById, petMapper)
    }

    @Test
    fun getPetByIdExecutesUseCase() {
        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(DataFactory.randomUuid())
        verify(getPetById, times(1)).execute(any(), any())
    }

    //<editor-fold desc="Success">
    @Test
    fun getPetByIdReturnsSuccess() {
        val pet = PetsFactory.makePet()
        val petView = PetsFactory.makePetView()

        stubPetMapperMapToView(petView, pet)

        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(DataFactory.randomUuid())

        verify(getPetById).execute(captor.capture(), any())
        captor.firstValue.onSuccess(pet)

        assert(petViewModel.getPetLiveData().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun getPetsReturnsDataOnSuccess() {
        val pet = PetsFactory.makePet()
        val petView = PetsFactory.makePetView()

        stubPetMapperMapToView(petView, pet)

        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(DataFactory.randomUuid())

        verify(getPetById).execute(captor.capture(), any())
        captor.firstValue.onSuccess(pet)

        assert(petViewModel.getPetLiveData().value?.data == petView)

    }

    @Test
    fun getPetsReturnsNoMessageOnSuccess() {
        val pet = PetsFactory.makePet()
        val petView = PetsFactory.makePetView()

        stubPetMapperMapToView(petView, pet)

        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(DataFactory.randomUuid())

        verify(getPetById).execute(captor.capture(), any())
        captor.firstValue.onSuccess(pet)

        assert(petViewModel.getPetLiveData().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getPetsReturnsError() {
        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(DataFactory.randomUuid())

        verify(getPetById).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assert(petViewModel.getPetLiveData().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getPetsFailsAndContainsNoData() {
        petViewModel.getPetLiveData()
        petViewModel.fetchPetById( DataFactory.randomUuid())

        verify(getPetById).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assert(petViewModel.getPetLiveData().value?.data == null)
    }

    @Test
    fun getPetsFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(DataFactory.randomUuid())

        verify(getPetById).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(petViewModel.getPetLiveData().value?.message == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getPetsReturnsLoading() {
        petViewModel.getPetLiveData()

        assert(petViewModel.getPetLiveData().value?.status == ResourceState.LOADING)
    }

    @Test
    fun getPetsContainsNoDataWhenLoading() {
        petViewModel.getPetLiveData()

        assert(petViewModel.getPetLiveData().value?.data == null)
    }

    @Test
    fun getPetsContainsNoMessageWhenLoading() {
        petViewModel.getPetLiveData()

        assert(petViewModel.getPetLiveData().value?.message == null)
    }
    //</editor-fold>

    private fun stubPetMapperMapToView(petView: PetView, pet: Pet) {
        whenever(petMapper.mapToView(pet))
                .thenReturn(petView)
    }
}