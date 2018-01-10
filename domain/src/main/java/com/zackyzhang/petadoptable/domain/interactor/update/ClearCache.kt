package com.zackyzhang.petadoptable.domain.interactor.update

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.CompletableUseCase
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by lei on 1/10/18.
 */
open class ClearCache @Inject constructor(val petsRepository: PetsRepository,
                                          threadExecutor: ThreadExecutor,
                                          postExecutionThread: PostExecutionThread) :
        CompletableUseCase<Unit>(threadExecutor, postExecutionThread) {

    override fun buildUseCaseObservable(params: Unit): Completable {
        return petsRepository.clearPets()
    }

}