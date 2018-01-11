package com.zackyzhang.petadoptable.domain.usecase.pet

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.test.factory.PetFactory
import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.update.UpdateFavoritePet
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class UpdateFavoritePetTest {

    private lateinit var updateFavoritePet: UpdateFavoritePet

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockPetsRepository: PetsRepository
    private lateinit var pet: Pet

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockPetsRepository = mock()
        updateFavoritePet = UpdateFavoritePet(mockPetsRepository, mockThreadExecutor, mockPostExecutionThread)
        pet = PetFactory.makePet()
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
//        val pet = PetFactory.makePet()
        updateFavoritePet.buildUseCaseObservable(pet)
        verify(mockPetsRepository)
                .saveToFavorite(pet)
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubPetsRepositorySaveToFavorite(Completable.complete())
        val testObserver = updateFavoritePet.buildUseCaseObservable(pet).test()
        testObserver.assertComplete()
    }

    private fun stubPetsRepositorySaveToFavorite(completable: Completable) {
        whenever(mockPetsRepository.saveToFavorite(pet))
                .thenReturn(completable)
    }
}