package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetById
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.presentation.model.PetView
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

/**
 * Created by lei on 12/29/17.
 */
open class BrowsePetViewModel @Inject constructor(private val getPetById: GetPetById,
                                                  private val petMapper: PetMapper) : ViewModel() {

    private val petsLiveData: MutableLiveData<Resource<PetView>> = MutableLiveData()

    override fun onCleared() {
        getPetById.dispose()
        super.onCleared()
    }

    fun getPetLiveData(): LiveData<Resource<PetView>> {
        return petsLiveData
    }

    fun fetchPetById(id: String) {
        petsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getPetById.execute(PetSubscriber(), id)
    }

    inner class PetSubscriber: DisposableSingleObserver<Pet>() {
        override fun onSuccess(t: Pet) {
            petsLiveData.postValue(Resource(ResourceState.SUCCESS,
                    petMapper.mapToView(t), null))
        }

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
            petsLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }

//        override fun onComplete() {}
//
//        override fun onNext(t: Pet) {
//            petsLiveData.postValue(Resource(ResourceState.SUCCESS,
//                    petMapper.mapToView(t), null))
//        }

    }
}