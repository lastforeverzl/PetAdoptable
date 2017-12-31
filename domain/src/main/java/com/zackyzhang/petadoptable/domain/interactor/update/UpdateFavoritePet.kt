package com.zackyzhang.petadoptable.domain.interactor.update

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.CompletableUseCase
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by lei on 12/28/17.
 */
open class UpdateFavoritePet @Inject constructor(val petsRepository: PetsRepository,
                                                 threadExecutor: ThreadExecutor,
                                                 postExecutionThread: PostExecutionThread) :
        CompletableUseCase<Pet>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Pet): Completable {
        return petsRepository.saveToFavorite(params)
    }

}