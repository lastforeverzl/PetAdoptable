package com.zackyzhang.petadoptable.domain.interactor.browse

import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.mapper.PetDetailMapper
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.model.PetDetail
import com.zackyzhang.petadoptable.domain.model.Shelter
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class GetPetDetailInfo @Inject constructor(
        val getShelterById: GetShelterById,
        val getPetById: GetPetById,
        val mapper: PetDetailMapper,
        protected val threadExecutor: ThreadExecutor,
        protected val postExecutionThread: PostExecutionThread) {

    private val disposables = CompositeDisposable()

    open fun execute(singleObserver: DisposableSingleObserver<PetDetail>,
                     petOptions: Map<String, String>,
                     shelterOptions: Map<String, String>) {

        val petSingle = getPetById.buildUseCaseObservable(petOptions)
        val shelterSingle = getShelterById.buildUseCaseObservable(shelterOptions)

        val observable = Single.zip(petSingle, shelterSingle,
                BiFunction<Pet, Shelter, PetDetail> { pet, shelter ->
                    mapper.mapToPetDetail(pet, shelter)
                })
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler) as Single<PetDetail>
        addDisposable(observable.subscribeWith(singleObserver))
    }

    fun dispose() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    protected fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

}
