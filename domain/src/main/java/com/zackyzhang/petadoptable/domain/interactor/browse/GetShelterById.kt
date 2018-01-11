package com.zackyzhang.petadoptable.domain.interactor.browse

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.SingleUseCase
import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.petadoptable.domain.repository.SheltersRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetShelterById @Inject constructor(private val sheltersRepository: SheltersRepository,
                                              threadExecutor: ThreadExecutor,
                                              postExecutionThread: PostExecutionThread) :
        SingleUseCase<Shelter, Map<String, String>>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Map<String, String>?): Single<Shelter> {
        return sheltersRepository.getShelterById(params!!)
    }

}