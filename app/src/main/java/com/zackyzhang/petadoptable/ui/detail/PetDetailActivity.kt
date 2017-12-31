package com.zackyzhang.petadoptable.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.glide.slider.library.SliderTypes.BaseSliderView
import com.glide.slider.library.SliderTypes.DefaultSliderView
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.model.PetViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_petdetail.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by lei on 12/28/17.
 */
class PetDetailActivity : AppCompatActivity(), AnkoLogger, BaseSliderView.OnSliderClickListener {

    companion object {
        val PET_ID = "PetDetailActivity:petId"

        fun newInstance(context: Context, id: String): Intent {
            val intent = Intent(context, PetDetailActivity::class.java)
            intent.putExtra(PET_ID, id)
            return intent
        }
    }

    lateinit var petId: String
    lateinit var pet: PetViewModel
    lateinit var urls: List<String>

    @Inject lateinit var mapper: PetMapper
    @Inject lateinit var viewModelFactory: BrowsePetViewModelFactory
    private lateinit var browsePetViewModel: BrowsePetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petdetail)

        petId = intent.getStringExtra(PET_ID)
        info("petId: $petId")

        browsePetViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowsePetViewModel::class.java)
        browsePetViewModel.fetchPetById(petId)
        browsePetViewModel.getPetLiveData().observe(this,
                Observer<Resource<PetView>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })

    }

    private fun handleDataState(resourceState: ResourceState, data: PetView?, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
        }
    }

    private fun setupScreenForLoadingState() {

    }

    private fun setupScreenForSuccess(data: PetView?) {
        if (data != null) {
            updateView(data)
        } else {

        }
    }

    private fun setupScreenForError(message: String?) {
        error(message)
    }

    private fun updateView(data: PetView) {
        info("pet: ${data.name} ${data.id} ${data.isFavorite}")
        pet = mapper.mapToViewModel(data)
        setupSlider()
        setupPetInfo()
    }

    private fun setupSlider() {
        urls = pet.medias
        info(urls)
        if (urls.isEmpty()) {
            val sliderView = DefaultSliderView(this)
            sliderView.image(R.drawable.no_image_placeholder)
                    .setBitmapTransformation(CenterCrop())
//                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this)
            photoSlider.addSlider(sliderView)
        } else {
            for (url in urls) {
                val sliderView = DefaultSliderView(this)
                sliderView.image(url)
                        .setBitmapTransformation(CenterCrop())
//                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener(this)
                photoSlider.addSlider(sliderView)
            }
        }
        photoSlider.stopAutoCycle()
    }

    private fun setupPetInfo() {
        animalName.text = pet.name
        animalInfo.text = pet.getPetInfo()
        pet.getAdoptionStatus()?.let { animalAdoptable.text = it } ?:run {
            animalAdoptable.visibility = View.GONE
        }
        pet.getLastUpdateInfo()?.let { animalUpdateDate.text = it } ?:run {
            animalUpdateDate.visibility = View.GONE
        }
        if (pet.description.isNotEmpty()) {
            animalDescription.text = pet.description
        } else {
            animalUpdateDate.visibility = View.GONE
        }

    }

    override fun onSliderClick(slider: BaseSliderView?) {
        info("slider position: ${ photoSlider.currentPosition }")
    }
}