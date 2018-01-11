package com.zackyzhang.petadoptable.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetDetailInfo
import com.zackyzhang.petadoptable.domain.interactor.update.UpdateFavoritePet
import com.zackyzhang.petadoptable.domain.model.PetDetail
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.PetDetailMapper
import com.zackyzhang.petadoptable.presentation.mapper.PetDetailToPetMapper
import com.zackyzhang.petadoptable.presentation.model.PetDetailView
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.presentation.test.factory.PetsFactory
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

@RunWith(JUnit4::class)
class BrowsePetViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var getPetDetailInfo: GetPetDetailInfo
    @Mock lateinit var petDetailMapper: PetDetailMapper
    @Mock lateinit var updateFavoritePet: UpdateFavoritePet
    @Mock lateinit var petDetailToPetMapper: PetDetailToPetMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<PetDetail>>

    private lateinit var petViewModel: BrowsePetViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor()
        getPetDetailInfo = mock()
        petDetailMapper = mock()
        updateFavoritePet = mock()
        petDetailToPetMapper = mock()
        petViewModel = BrowsePetViewModel(getPetDetailInfo, updateFavoritePet, petDetailMapper, petDetailToPetMapper)
    }

    @Test
    fun getPetByIdExecutesUseCase() {
        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(randomUuid(), randomUuid(), randomUuid())
        verify(getPetDetailInfo, times(1)).execute(any(), any(), any())
    }

    //<editor-fold desc="Success">
    @Test
    fun getPetByIdReturnsSuccess() {
        val petDetail = PetsFactory.makePetDetail()
        val petDetailView = PetsFactory.makePetDetailView()

        stubPetDetailMapperMapToView(petDetailView, petDetail)

        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(randomUuid(), randomUuid(), randomUuid())

        verify(getPetDetailInfo).execute(captor.capture(), any(), any())
        captor.firstValue.onSuccess(petDetail)

        assert(petViewModel.getPetLiveData().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun getPetsReturnsDataOnSuccess() {
        val petDetail = PetsFactory.makePetDetail()
        val petDetailView = PetsFactory.makePetDetailView()

        stubPetDetailMapperMapToView(petDetailView, petDetail)

        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(randomUuid(), randomUuid(), randomUuid())

        verify(getPetDetailInfo).execute(captor.capture(), any(), any())
        captor.firstValue.onSuccess(petDetail)

        assert(petViewModel.getPetLiveData().value?.data == petDetailView)

    }

    @Test
    fun getPetsReturnsNoMessageOnSuccess() {
        val petDetail = PetsFactory.makePetDetail()
        val petDetailView = PetsFactory.makePetDetailView()

        stubPetDetailMapperMapToView(petDetailView, petDetail)

        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(randomUuid(), randomUuid(), randomUuid())

        verify(getPetDetailInfo).execute(captor.capture(), any(), any())
        captor.firstValue.onSuccess(petDetail)

        assert(petViewModel.getPetLiveData().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getPetsReturnsError() {
        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(randomUuid(), randomUuid(), randomUuid())

        verify(getPetDetailInfo).execute(captor.capture(), any(), any())
        captor.firstValue.onError(RuntimeException())

        assert(petViewModel.getPetLiveData().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getPetsFailsAndContainsNoData() {
        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(randomUuid(), randomUuid(), randomUuid())

        verify(getPetDetailInfo).execute(captor.capture(), any(), any())
        captor.firstValue.onError(RuntimeException())

        assert(petViewModel.getPetLiveData().value?.data == null)
    }

    @Test
    fun getPetsFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        petViewModel.getPetLiveData()
        petViewModel.fetchPetById(randomUuid(), randomUuid(), randomUuid())

        verify(getPetDetailInfo).execute(captor.capture(), any(), any())
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

    private fun stubPetDetailMapperMapToView(petDetailView: PetDetailView, petDetail: PetDetail) {
        whenever(petDetailMapper.mapToView(petDetail))
                .thenReturn(petDetailView)
    }
}