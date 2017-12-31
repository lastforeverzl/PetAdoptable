package com.zackyzhang.petadoptable.domain.interactor.browse

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.FlowableUseCase
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by lei on 12/29/17.
 */
open class GetPetById @Inject constructor(val petsRepository: PetsRepository,
                                               threadExecutor: ThreadExecutor,
                                               postExecutionThread: PostExecutionThread) :
        FlowableUseCase<Pet, String>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: String?): Flowable<Pet> {
        return petsRepository.getPetById(params!!)
    }

}