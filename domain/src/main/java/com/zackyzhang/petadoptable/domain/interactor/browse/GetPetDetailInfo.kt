package com.zackyzhang.petadoptable.domain.interactor.browse

/**
 * Created by lei on 12/29/17.
 */
//open class GetPetDetailInfo @Inject constructor(val petsRepository: PetsRepository,
//                                                val getPetById: GetPetById,
//                                                threadExecutor: ThreadExecutor,
//                                                postExecutionThread: PostExecutionThread) {
//
//    open fun execute(petId: String, shelterId: String) {
//
//        val letter = Flowable.just("Alpha", "Beta", "Gamma", "Delta", "Epsilon")
//        val number = Flowable.just(1, 2, 3, 4, 5)
//
//        val petObservable = getPetById.buildUseCaseObservable(petId)
//        val shelterObservable = petsRepository.getPetById(petId)
//        Flowable.zip(petObservable, shelterObservable, BiFunction<Pet, Pet, Pet> { t1, t2 -> codePair(t1, t2) })
//    }
//
//    fun codePair(letter: Pet, number: Pet): Pet {
//        return letter
//    }
//}
