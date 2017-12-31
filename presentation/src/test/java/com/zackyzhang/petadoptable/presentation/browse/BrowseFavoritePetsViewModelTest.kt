package com.zackyzhang.petadoptable.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.zackyzhang.petadoptable.domain.interactor.browse.GetFavoritePets
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory
import com.zackyzhang.petadoptable.presentation.test.factory.PetsFactory
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

/**
 * Created by lei on 12/8/17.
 */
@RunWith(JUnit4::class)
class BrowseFavoritePetsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var getFavoritePets: GetFavoritePets
    @Mock lateinit var petMapper: PetMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Pet>>>

    private lateinit var favoritePetsViewModel: BrowseFavoritePetsViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor()
        getFavoritePets = mock()
        petMapper = mock()
        favoritePetsViewModel = BrowseFavoritePetsViewModel(getFavoritePets, petMapper)
    }

    @Test
    fun getPetsExecutesUseCase() {
        favoritePetsViewModel.getFavoritePetsLiveData()
        favoritePetsViewModel.fetchFavoritePets()
        verify(getFavoritePets, times(1)).execute(any(), eq(null))
    }

    //<editor-fold desc="Success">
    @Test
    fun getFavoritePetsReturnsSuccess() {
        val list = PetsFactory.makePetList(2)
        val viewList = PetsFactory.makePetViewList(2)

        stubPetMapperMapToView(viewList[0], list[0])
        stubPetMapperMapToView(viewList[1], list[1])

        favoritePetsViewModel.getFavoritePetsLiveData()
        favoritePetsViewModel.fetchFavoritePets()

        verify(getFavoritePets).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(favoritePetsViewModel.getFavoritePetsLiveData().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun getPetsReturnsDataOnSuccess() {
        val list = PetsFactory.makePetList(2)
        val viewList = PetsFactory.makePetViewList(2)

        stubPetMapperMapToView(viewList[0], list[0])
        stubPetMapperMapToView(viewList[1], list[1])

        favoritePetsViewModel.getFavoritePetsLiveData()
        favoritePetsViewModel.fetchFavoritePets()

        verify(getFavoritePets).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(favoritePetsViewModel.getFavoritePetsLiveData().value?.data == viewList)

    }

    @Test
    fun getPetsReturnsNoMessageOnSuccess() {
        val list = PetsFactory.makePetList(2)
        val viewList = PetsFactory.makePetViewList(2)

        stubPetMapperMapToView(viewList[0], list[0])
        stubPetMapperMapToView(viewList[1], list[1])

        favoritePetsViewModel.getFavoritePetsLiveData()
        favoritePetsViewModel.fetchFavoritePets()

        verify(getFavoritePets).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(favoritePetsViewModel.getFavoritePetsLiveData().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getPetsReturnsError() {
        favoritePetsViewModel.getFavoritePetsLiveData()
        favoritePetsViewModel.fetchFavoritePets()

        verify(getFavoritePets).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assert(favoritePetsViewModel.getFavoritePetsLiveData().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getPetsFailsAndContainsNoData() {
        favoritePetsViewModel.getFavoritePetsLiveData()
        favoritePetsViewModel.fetchFavoritePets()

        verify(getFavoritePets).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assert(favoritePetsViewModel.getFavoritePetsLiveData().value?.data == null)
    }

    @Test
    fun getPetsFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        favoritePetsViewModel.getFavoritePetsLiveData()
        favoritePetsViewModel.fetchFavoritePets()

        verify(getFavoritePets).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(favoritePetsViewModel.getFavoritePetsLiveData().value?.message == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getPetsReturnsLoading() {
        favoritePetsViewModel.getFavoritePetsLiveData()

        assert(favoritePetsViewModel.getFavoritePetsLiveData().value?.status == ResourceState.LOADING)
    }

    @Test
    fun getPetsContainsNoDataWhenLoading() {
        favoritePetsViewModel.getFavoritePetsLiveData()

        assert(favoritePetsViewModel.getFavoritePetsLiveData().value?.data == null)
    }

    @Test
    fun getPetsContainsNoMessageWhenLoading() {
        favoritePetsViewModel.getFavoritePetsLiveData()

        assert(favoritePetsViewModel.getFavoritePetsLiveData().value?.message == null)
    }
    //</editor-fold>

    private fun stubPetMapperMapToView(petView: PetView, pet: Pet) {
        whenever(petMapper.mapToView(pet))
                .thenReturn(petView)
    }
}