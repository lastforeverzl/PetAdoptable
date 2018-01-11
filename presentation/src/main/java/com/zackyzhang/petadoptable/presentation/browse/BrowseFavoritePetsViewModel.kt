package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zackyzhang.petadoptable.domain.interactor.browse.GetFavoritePets
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.PetMapper
import com.zackyzhang.petadoptable.presentation.model.PetView
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

open class BrowseFavoritePetsViewModel @Inject internal constructor(
        private val getFavoritePets: GetFavoritePets,
        private val petMapper: PetMapper) : ViewModel() {

    val favoritePetsLiveData: MutableLiveData<Resource<List<PetView>>> = MutableLiveData()

    override fun onCleared() {
        getFavoritePets.dispose()
        super.onCleared()
    }

    fun getFavoritePetsLiveData(): LiveData<Resource<List<PetView>>> {
        return favoritePetsLiveData
    }


    fun fetchFavoritePets() {
        favoritePetsLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getFavoritePets.execute(PetSubscriber())
    }

    inner class PetSubscriber: DisposableSubscriber<List<Pet>>() {

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
            favoritePetsLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }

        override fun onComplete() {}

        override fun onNext(t: List<Pet>) {
            favoritePetsLiveData.postValue(Resource(ResourceState.SUCCESS,
                    t.map { petMapper.mapToView(it) }, null))
        }

    }

}