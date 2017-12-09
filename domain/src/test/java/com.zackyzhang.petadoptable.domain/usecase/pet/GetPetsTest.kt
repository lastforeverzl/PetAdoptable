package com.zackyzhang.petadoptable.domain.usecase.pet

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.test.factory.PetFactory
import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPets
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by lei on 12/7/17.
 */
@RunWith(JUnit4::class)
class GetPetsTest {

    private lateinit var getPets: GetPets

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockPetsRepository: PetsRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockPetsRepository = mock()
        getPets = GetPets(mockPetsRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getPets.currentOffset = 50
        getPets.buildUseCaseObservable(mutableMapOf())
        verify(mockPetsRepository)
                .getPets(mapOf(Pair("offset", getPets.currentOffset.toString())))
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubPetsRepositoryGetPets(Flowable.just(PetFactory.makePetList(2)))
        val testObserver = getPets.buildUseCaseObservable(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val pets = PetFactory.makePetList(2)
        stubPetsRepositoryGetPets(Flowable.just(pets))
        val testObserver = getPets.buildUseCaseObservable(mutableMapOf()).test()
        testObserver.assertValue(pets)
    }

    private fun stubPetsRepositoryGetPets(flowable: Flowable<List<Pet>>) {
        whenever(mockPetsRepository.getPets(any()))
                .thenReturn(flowable)
    }
}