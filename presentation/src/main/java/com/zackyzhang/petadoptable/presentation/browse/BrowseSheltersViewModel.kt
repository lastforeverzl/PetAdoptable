package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zackyzhang.petadoptable.domain.interactor.browse.GetShelters
import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.ShelterMapper
import com.zackyzhang.petadoptable.presentation.model.ShelterView
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

open class BrowseSheltersViewModel @Inject constructor(
        private val getShelters: GetShelters,
        private val shelterMapper: ShelterMapper) : ViewModel() {

    private val sheltersLiveData: MutableLiveData<Resource<List<ShelterView>>> = MutableLiveData()

    override fun onCleared() {
        getShelters.dispose()
        super.onCleared()
    }

    fun getShelters(): LiveData<Resource<List<ShelterView>>> {
        return sheltersLiveData
    }

    fun fetchShelters(key: String, zipCode: String, offset: Int) {
        val options = mutableMapOf<String, String>()
        options["key"] = key
        options["location"] = zipCode
        options["offset"] = Integer.toString(offset)
        sheltersLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getShelters.execute(ShelterSubscriber(), options)
    }

    inner class ShelterSubscriber: DisposableSubscriber<List<Shelter>>() {

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
            sheltersLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }

        override fun onComplete() {}

        override fun onNext(t: List<Shelter>) {
            sheltersLiveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { shelterMapper.mapToView(it) }, null))
        }

    }

}