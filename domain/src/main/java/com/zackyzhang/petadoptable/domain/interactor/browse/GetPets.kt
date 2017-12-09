package com.zackyzhang.petadoptable.domain.interactor.browse

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.interactor.FlowableUseCase
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

/**
 * Use case used for retrieving a [List] of [Pet] instances from the [PetsRepository]
 */
open class GetPets @Inject constructor(val petsRepository: PetsRepository,
                                       threadExecutor: ThreadExecutor,
                                       postExecutionThread: PostExecutionThread):
        FlowableUseCase<List<Pet>, Map<String, String>>(threadExecutor, postExecutionThread) {

    private val DEFAULT_LIMIT = 25
    var currentOffset = 0 // For test purpose, not set private.

    public override fun buildUseCaseObservable(params: Map<String, String>?): Flowable<List<Pet>> {
        val newOptions = mutableMapOf<String, String>()
        params!!.let { newOptions.putAll(it) }
        newOptions.put("offset", currentOffset.toString())
        return petsRepository.getPets(newOptions)
    }

    override fun execute(observer: DisposableSubscriber<List<Pet>>, params: Map<String, String>?) {
        val observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
                .doOnError { decreaseOffset() } as Flowable<List<Pet>>
        addDisposable(observable.subscribeWith(observer))
        increaseOffset()
    }

    private fun increaseOffset() {
        currentOffset += DEFAULT_LIMIT
    }

    private fun decreaseOffset() {
        currentOffset -= DEFAULT_LIMIT
    }
}
