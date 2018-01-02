package com.zackyzhang.petadoptable.presentation.browse

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zackyzhang.petadoptable.domain.interactor.browse.GetPetDetailInfo
import com.zackyzhang.petadoptable.domain.interactor.update.UpdateFavoritePet
import com.zackyzhang.petadoptable.domain.model.PetDetail
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.mapper.PetDetailMapper
import com.zackyzhang.petadoptable.presentation.mapper.PetDetailToPetMapper
import com.zackyzhang.petadoptable.presentation.model.PetDetailView
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

/**
 * Created by lei on 12/29/17.
 */
open class BrowsePetViewModel @Inject constructor(private val getPetDetailInfo: GetPetDetailInfo,
                                                  private val updateFavoritePet: UpdateFavoritePet,
                                                  private val petDetailMapper: PetDetailMapper,
                                                  private val petDetailToPetMapper: PetDetailToPetMapper) :
        ViewModel() {

    private val petLiveData: MutableLiveData<Resource<PetDetailView>> = MutableLiveData()

    override fun onCleared() {
        getPetDetailInfo.dispose()
        updateFavoritePet.dispose()
        super.onCleared()
    }

    fun getPetLiveData(): LiveData<Resource<PetDetailView>> {
        return petLiveData
    }

    fun fetchPetById(key: String, petId: String, shelterId: String) {
        val options = mutableMapOf<String, String>()
        options["key"] = key
        options["id"] = shelterId
        petLiveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getPetDetailInfo.execute(PetSubscriber(), petId, options)
    }

    fun updateFavoriteStatus(petDetailView: PetDetailView) {
        updateFavoritePet.execute(UpdateSubscriber(), petDetailToPetMapper.mapToView(petDetailView))
    }

    inner class PetSubscriber: DisposableSingleObserver<PetDetail>() {
        override fun onSuccess(t: PetDetail) {
            petLiveData.postValue(Resource(ResourceState.SUCCESS,
                    petDetailMapper.mapToView(t), null))
        }

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
            petLiveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }

    }

    inner class UpdateSubscriber : DisposableCompletableObserver() {
        override fun onComplete() {
            println("UpdateSubscriber onComplete")
        }

        override fun onError(e: Throwable) {

        }

    }
}