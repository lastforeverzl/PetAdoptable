package com.zackyzhang.petadoptable.domain.interactor.browse

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.FlowableUseCase
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Flowable
import javax.inject.Inject

open class GetSearchPets @Inject constructor(private val petsRepository: PetsRepository,
                                             threadExecutor: ThreadExecutor,
                                             postExecutionThread: PostExecutionThread):
        FlowableUseCase<List<Pet>, Map<String, String>>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Map<String, String>?): Flowable<List<Pet>> {
        return petsRepository.getSearchPets(params!!)
    }

}