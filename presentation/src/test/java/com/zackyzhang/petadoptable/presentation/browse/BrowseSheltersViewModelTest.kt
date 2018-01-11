package com.zackyzhang.shelteradoptable.presentation.browse

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelters
import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.petadoptable.presentation.browse.BrowseSheltersViewModel
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.ShelterMapper
import com.zackyzhang.petadoptable.presentation.model.ShelterView
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory.Factory.randomInt
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.shelteradoptable.presentation.test.factory.SheltersFactory
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

@RunWith(JUnit4::class)
class BrowseSheltersViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var getShelters: GetShelters
    @Mock lateinit var shelterMapper: ShelterMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<Shelter>>>

    private lateinit var sheltersViewModel: BrowseSheltersViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor()
        getShelters = mock()
        shelterMapper = mock()
        sheltersViewModel = BrowseSheltersViewModel(getShelters, shelterMapper)
    }

    @Test
    fun getSheltersExecutesUseCase() {
        sheltersViewModel.getShelters()
        sheltersViewModel.fetchShelters(randomUuid(), randomUuid(), randomInt())
        verify(getShelters, times(1)).execute(any(), any())
    }

    //<editor-fold desc="Success">
    @Test
    fun getSheltersReturnsSuccess() {
        val list = SheltersFactory.makeShelterList(2)
        val viewList = SheltersFactory.makeShelterViewList(2)
        val key = randomUuid()
        val location = randomUuid()
        val offset = randomInt()

        stubShelterMapperMapToView(viewList[0], list[0])
        stubShelterMapperMapToView(viewList[1], list[1])

        sheltersViewModel.getShelters()
        sheltersViewModel.fetchShelters(key, location, offset)

//        verify(getShelters).execute(captor.capture(), eq(mapOf(Pair("key", key), Pair("location", location))))
        verify(getShelters).execute(captor.capture(), any())
        captor.firstValue.onNext(list)

        assert(sheltersViewModel.getShelters().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun getSheltersReturnsDataOnSuccess() {
        val list = SheltersFactory.makeShelterList(2)
        val viewList = SheltersFactory.makeShelterViewList(2)

        stubShelterMapperMapToView(viewList[0], list[0])
        stubShelterMapperMapToView(viewList[1], list[1])

        sheltersViewModel.getShelters()
        sheltersViewModel.fetchShelters(randomUuid(), randomUuid(), randomInt())

        verify(getShelters).execute(captor.capture(), any())
        captor.firstValue.onNext(list)

        assert(sheltersViewModel.getShelters().value?.data == viewList)

    }

    @Test
    fun getSheltersReturnsNoMessageOnSuccess() {
        val list = SheltersFactory.makeShelterList(2)
        val viewList = SheltersFactory.makeShelterViewList(2)

        stubShelterMapperMapToView(viewList[0], list[0])
        stubShelterMapperMapToView(viewList[1], list[1])

        sheltersViewModel.getShelters()
        sheltersViewModel.fetchShelters(randomUuid(), randomUuid(), randomInt())

        verify(getShelters).execute(captor.capture(), any())
        captor.firstValue.onNext(list)

        assert(sheltersViewModel.getShelters().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getSheltersReturnsError() {
        sheltersViewModel.getShelters()
        sheltersViewModel.fetchShelters(randomUuid(), randomUuid(), randomInt())

        verify(getShelters).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assert(sheltersViewModel.getShelters().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getSheltersFailsAndContainsNoData() {
        sheltersViewModel.getShelters()
        sheltersViewModel.fetchShelters(randomUuid(), randomUuid(), randomInt())

        verify(getShelters).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException())

        assert(sheltersViewModel.getShelters().value?.data == null)
    }

    @Test
    fun getSheltersFailsAndContainsMessage() {
        val errorMessage = randomUuid()
        sheltersViewModel.getShelters()
        sheltersViewModel.fetchShelters(randomUuid(), randomUuid(), randomInt())

        verify(getShelters).execute(captor.capture(), any())
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(sheltersViewModel.getShelters().value?.message == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getSheltersReturnsLoading() {
        sheltersViewModel.getShelters()

        assert(sheltersViewModel.getShelters().value?.status == ResourceState.LOADING)
    }

    @Test
    fun getSheltersContainsNoDataWhenLoading() {
        sheltersViewModel.getShelters()

        assert(sheltersViewModel.getShelters().value?.data == null)
    }

    @Test
    fun getSheltersContainsNoMessageWhenLoading() {
        sheltersViewModel.getShelters()

        assert(sheltersViewModel.getShelters().value?.message == null)
    }
    //</editor-fold>

    private fun stubShelterMapperMapToView(shelterView: ShelterView, shelter: Shelter) {
        whenever(shelterMapper.mapToView(shelter))
                .thenReturn(shelterView)
    }
}