package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPets
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.presentation.model.PetView
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

open class BrowsePetsViewModel @Inject internal constructor(
        private val getPets: GetPets,
        private val petMapper: PetMapper) : ViewModel() {

    private val petsLiveData: MutableLiveData<Resource<List<PetView>>> = MutableLiveData()

    override fun onCleared() {
        getPets.dispose()
        super.onCleared()
    }

    fun getPetsLiveData():LiveData<Resource<List<PetView>>> {
        return petsLiveData
    }

    fun fetchPets(key: String, zipCode: String, offset: Int, animal: String) {
        val options = mutableMapOf<String, String>()
        options["key"] = key
        options["location"] = zipCode
        options["offset"] = Integer.toString(offset)
        options["animal"] = animal
        petsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getPets.execute(PetSubscriber(), options)
    }

    inner class PetSubscriber: DisposableSubscriber<List<Pet>>() {

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
            petsLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }

        override fun onComplete() {}

        override fun onNext(t: List<Pet>) {
            petsLiveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { petMapper.mapToView(it) }, null))
        }

    }
}