package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zackyzhang.petadoptable.domain.interactor.browse.GetFavoriteStatus
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

/**
 * Created by lei on 1/1/18.
 */
open class CheckFavoriteStatusViewModel @Inject constructor(private val getFavoriteStatus: GetFavoriteStatus) :
        ViewModel() {

    private val statusLiveData: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    fun getStatusLiveData(): LiveData<Resource<Boolean>> {
        return statusLiveData
    }

    fun fetchFavoriteStatus(petId: String) {
        getFavoriteStatus.execute(UpdateSubscriber(), petId)
    }

    inner class UpdateSubscriber : DisposableSingleObserver<Boolean>() {
        override fun onSuccess(t: Boolean) {
            statusLiveData.postValue(Resource(ResourceState.SUCCESS, t, null))
        }

        override fun onError(e: Throwable) {
            statusLiveData.postValue(Resource(ResourceState.ERROR, null, e.message))
        }

    }
}