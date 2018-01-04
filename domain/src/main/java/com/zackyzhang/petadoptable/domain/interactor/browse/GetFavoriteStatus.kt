package com.zackyzhang.petadoptable.domain.interactor.browse

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.SingleUseCase
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by lei on 1/2/18.
 */
open class GetFavoriteStatus @Inject constructor(val petsRepository: PetsRepository,
                                                 threadExecutor: ThreadExecutor,
                                                 postExecutionThread: PostExecutionThread) :
        SingleUseCase<Boolean, String>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: String?): Single<Boolean> {
        return petsRepository.isFavoritePet(params!!)
    }

}