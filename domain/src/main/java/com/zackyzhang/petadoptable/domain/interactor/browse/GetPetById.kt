package com.zackyzhang.petadoptable.domain.interactor.browse

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.SingleUseCase
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Single
import javax.inject.Inject

open class GetPetById @Inject constructor(private val petsRepository: PetsRepository,
                                          threadExecutor: ThreadExecutor,
                                          postExecutionThread: PostExecutionThread) :
        SingleUseCase<Pet, Map<String, String>>(threadExecutor, postExecutionThread) {

    public override fun buildUseCaseObservable(params: Map<String, String>?): Single<Pet> {
        return petsRepository.getPetById(params!!)
    }

}