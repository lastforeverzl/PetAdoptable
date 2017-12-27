package com.zackyzhang.petadoptable.domain.interactor.browse

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.FlowableUseCase
import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.petadoptable.domain.repository.SheltersRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Use case used for retrieving a [List] of [Shelter] instances from the [SheltersRepository]
 */
open class GetShelters @Inject constructor(val sheltersRepository: SheltersRepository,
                                           threadExecutor: ThreadExecutor,
                                           postExecutionThread: PostExecutionThread) :
        FlowableUseCase<List<Shelter>, Map<String, String>>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Map<String, String>?): Flowable<List<Shelter>> {
        return sheltersRepository.getShelters(params!!)
    }

}
