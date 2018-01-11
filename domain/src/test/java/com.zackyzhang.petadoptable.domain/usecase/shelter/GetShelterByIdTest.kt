package com.zackyzhang.petadoptable.domain.usecase.shelter

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelterById
import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.petadoptable.domain.repository.SheltersRepository
import com.zackyzhang.shelteradoptable.domain.test.factory.ShelterFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetShelterByIdTest {

    private lateinit var getShelterById: GetShelterById

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockSheltersRepository: SheltersRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockSheltersRepository = mock()
        getShelterById = GetShelterById(mockSheltersRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getShelterById.buildUseCaseObservable(mutableMapOf())
        verify(mockSheltersRepository)
                .getShelterById(mutableMapOf())
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubPetsRepositoryGetShelterById(Single.just(ShelterFactory.makeShelter()))
        val testObserver = getShelterById.buildUseCaseObservable(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val shelter = ShelterFactory.makeShelter()
        stubPetsRepositoryGetShelterById(Single.just(shelter))
        val testObserver = getShelterById.buildUseCaseObservable(mutableMapOf()).test()
        testObserver.assertValue(shelter)
    }

    private fun stubPetsRepositoryGetShelterById(single: Single<Shelter>) {
        whenever(mockSheltersRepository.getShelterById(any()))
                .thenReturn(single)
    }
}