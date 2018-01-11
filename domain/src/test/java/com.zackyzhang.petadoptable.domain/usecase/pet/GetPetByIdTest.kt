package com.zackyzhang.petadoptable.domain.usecase.pet

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetById
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

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

//    @Test
//    fun buildUseCaseObservableCallsRepository() {
//        getPetById.buildUseCaseObservable(anyString())
//        verify(mockPetsRepository)
//                .getPetById(anyString())
//    }
//
//    @Test
//    fun buildUseCaseObservableCompletes() {
//        stubPetsRepositoryGetPets(Single.just(PetFactory.makePet()))
//        val testObserver = getPetById.buildUseCaseObservable(anyString()).test()
//        testObserver.assertComplete()
//    }
//
//    @Test
//    fun buildUseCaseObservableReturnsData() {
//        val pet = PetFactory.makePet()
//        stubPetsRepositoryGetPets(Single.just(pet))
//        val testObserver = getPetById.buildUseCaseObservable(anyString()).test()
//        testObserver.assertValue(pet)
//    }

    private fun stubPetsRepositoryGetPets(single: Single<Pet>) {
        whenever(mockPetsRepository.getPetById(any()))
                .thenReturn(single)
    }
}