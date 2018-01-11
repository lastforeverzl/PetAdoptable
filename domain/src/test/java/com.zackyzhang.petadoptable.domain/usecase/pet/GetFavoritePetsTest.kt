package com.zackyzhang.petadoptable.domain.usecase.pet

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.test.factory.PetFactory
import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.browse.GetFavoritePets
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GetFavoritePetsTest {

    private lateinit var getFavoritePets: GetFavoritePets

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockPetsRepository: PetsRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockPetsRepository = mock()
        getFavoritePets = GetFavoritePets(mockPetsRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getFavoritePets.buildUseCaseObservable()
        verify(mockPetsRepository)
                .getFavoritePets()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubPetsRepositoryGetFavoritePets(Flowable.just(PetFactory.makePetList(2)))
        val testObserver = getFavoritePets.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val pets = PetFactory.makePetList(2)
        stubPetsRepositoryGetFavoritePets(Flowable.just(pets))
        val testObserver = getFavoritePets.buildUseCaseObservable().test()
        testObserver.assertValue(pets)
    }

    private fun stubPetsRepositoryGetFavoritePets(flowable: Flowable<List<Pet>>) {
        whenever(mockPetsRepository.getFavoritePets())
                .thenReturn(flowable)
    }

}