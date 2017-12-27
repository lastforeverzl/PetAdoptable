package com.zackyzhang.shelteradoptable.domain.usecase.shelter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelters
import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.petadoptable.domain.repository.SheltersRepository
import com.zackyzhang.shelteradoptable.domain.test.factory.ShelterFactory
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by lei on 12/22/17.
 */
@RunWith(JUnit4::class)
class GetSheltersTest {

    private lateinit var getShelters: GetShelters

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockSheltersRepository: SheltersRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockSheltersRepository = mock()
        getShelters = GetShelters(mockSheltersRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getShelters.buildUseCaseObservable(mutableMapOf())
        verify(mockSheltersRepository)
                .getShelters(mutableMapOf())
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubSheltersRepositoryGetShelters(Flowable.just(ShelterFactory.makeShelterList(2)))
        val testObserver = getShelters.buildUseCaseObservable(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val shelters = ShelterFactory.makeShelterList(2)
        stubSheltersRepositoryGetShelters(Flowable.just(shelters))
        val testObserver = getShelters.buildUseCaseObservable(mutableMapOf()).test()
        testObserver.assertValue(shelters)
    }

    private fun stubSheltersRepositoryGetShelters(flowable: Flowable<List<Shelter>>) {
        whenever(mockSheltersRepository.getShelters(any()))
                .thenReturn(flowable)
    }
}