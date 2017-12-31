package com.zackyzhang.petadoptable.domain.usecase.pet

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.test.factory.PetFactory
import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetById
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString

/**
 * Created by lei on 12/29/17.
 */
@RunWith(JUnit4::class)
class GetPetByIdTest {

    private lateinit var getPetById: GetPetById

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockPetsRepository: PetsRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockPetsRepository = mock()
        getPetById = GetPetById(mockPetsRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getPetById.buildUseCaseObservable(anyString())
        verify(mockPetsRepository)
                .getPetById(anyString())
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubPetsRepositoryGetPets(Flowable.just(PetFactory.makePet()))
        val testObserver = getPetById.buildUseCaseObservable(anyString()).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val pet = PetFactory.makePet()
        stubPetsRepositoryGetPets(Flowable.just(pet))
        val testObserver = getPetById.buildUseCaseObservable(anyString()).test()
        testObserver.assertValue(pet)
    }

    private fun stubPetsRepositoryGetPets(flowable: Flowable<Pet>) {
        whenever(mockPetsRepository.getPetById(any()))
                .thenReturn(flowable)
    }
}