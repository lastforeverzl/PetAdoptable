package com.zackyzhang.petadoptable.ui.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.glide.slider.library.SliderTypes.BaseSliderView
import com.glide.slider.library.SliderTypes.DefaultSliderView
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.model.PetDetailView
import com.zackyzhang.petadoptable.ui.BuildConfig
import com.zackyzhang.petadoptable.ui.GalleryActivity.GalleryActivity
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.mapper.PetDetailMapper
import com.zackyzhang.petadoptable.ui.model.PetDetailViewModel
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
        val SHELTER_ID = "PetDetailActivity:shelterId"

        fun newInstance(context: Context, petId: String, shelterId: String): Intent {
            val intent = Intent(context, PetDetailActivity::class.java)
            intent.putExtra(PET_ID, petId)
            intent.putExtra(SHELTER_ID, shelterId)
            return intent
        }
    }

    lateinit var petId: String
    lateinit var shelterId: String
    lateinit var petDetail: PetDetailViewModel
    lateinit var urls: List<String>
    lateinit var favoriteIcon: MenuItem

    @Inject lateinit var mapper: PetDetailMapper
    @Inject lateinit var viewModelFactory: BrowsePetViewModelFactory
    private lateinit var browsePetViewModel: BrowsePetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_petdetail)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""

        petId = intent.getStringExtra(PET_ID)
        shelterId = intent.getStringExtra(SHELTER_ID)
        info("petId: $petId")

        browsePetViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowsePetViewModel::class.java)
        browsePetViewModel.fetchPetById(BuildConfig.PETFINDER_API_KEY, petId, shelterId)
        browsePetViewModel.getPetLiveData().observe(this,
                Observer<Resource<PetDetailView>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_pet_detail, menu)
        favoriteIcon = menu!!.findItem(R.id.favoritePet)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favoritePet -> {
                //todo("implement update favorite pet status")
                if (petDetail.petIsFavorite) {
                    changeFavoriteStatus(false)
                } else {
                    changeFavoriteStatus(true)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleDataState(resourceState: ResourceState, data: PetDetailView?, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
        }
    }

    private fun setupScreenForLoadingState() {

    }

    private fun setupScreenForSuccess(data: PetDetailView?) {
        if (data != null) {
            updateView(data)
        } else {

        }
    }

    private fun setupScreenForError(message: String?) {
        error(message)
    }

    private fun updateView(data: PetDetailView) {
        info("pet: ${data.petName} ${data.petId} ${data.petIsFavorite} ${data.shelterAddress}")
        petDetail = mapper.mapToViewModel(data)
        if (petDetail.petIsFavorite) {
            favoriteIcon.setIcon(R.drawable.ic_favorite_white_24px)
        } else {
            favoriteIcon.setIcon(R.drawable.ic_favorite_border_24dp)
        }
        setupSlider()
        setupPetInfo()
        setupShelterInfo()
    }

    private fun setupSlider() {
        urls = petDetail.petMedias
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
        animalName.text = petDetail.petName
        animalInfo.text = petDetail.getPetInfo()
        petDetail.getAdoptionStatus()?.let { animalAdoptable.text = it } ?:run {
            animalAdoptable.visibility = View.GONE
        }
        petDetail.getLastUpdateInfo()?.let { animalUpdateDate.text = it } ?:run {
            animalUpdateDate.visibility = View.GONE
        }
        if (petDetail.petDescription.isNotEmpty()) {
            animalDescription.text = petDetail.petDescription
        } else {
            animalUpdateDate.visibility = View.GONE
        }
    }

    private fun setupShelterInfo() {
        if (petDetail.shelterName.isNotEmpty()) {
            shelterName.text = petDetail.shelterName
        } else {
            shelterName.visibility = View.GONE
        }
        if (petDetail.shelterAddress.isNotBlank()) {
            shelterAddress.text = petDetail.shelterAddress
        } else {
            shelterAddress.visibility = View.GONE
        }
        if (petDetail.shelterPhone.isNotEmpty()) {
            shelterPhone.text = petDetail.shelterPhone
        } else {
            shelterPhone.visibility = View.GONE
        }
        if (petDetail.shelterEmail.isNotEmpty()) {
            btEmail.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(petDetail.shelterEmail))
                intent.resolveActivity(packageManager)?.let { startActivity(intent) }
            }
        } else {
            btEmail.setBackgroundResource(R.drawable.pet_detail_button_gray)
        }
        if (petDetail.shelterLatitude.isEmpty() or petDetail.shelterLongitude.isEmpty()) {
            btGetDirection.setBackgroundResource(R.drawable.pet_detail_button_gray)
        }
        shelterPhone.setOnClickListener {
            val call = Uri.parse("tel: ${ petDetail.shelterPhone.trim() }")
            val intent = Intent(Intent.ACTION_DIAL, call)
            startActivity(intent)
        }
        btGetDirection.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:${petDetail.shelterLatitude},${petDetail.shelterLongitude}?q=${petDetail.shelterAddress}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package` = "com.google.android.apps.maps"
            startActivity(mapIntent)
        }
    }

    private fun changeFavoriteStatus(isFavorite: Boolean) {
        petDetail.petIsFavorite = isFavorite
        browsePetViewModel.updateFavoriteStatus(mapper.mapFromViewModel(petDetail))
        if (isFavorite) {
            favoriteIcon.setIcon(R.drawable.ic_favorite_white_24px)
        } else {
            favoriteIcon.setIcon(R.drawable.ic_favorite_border_24dp)
        }
    }

    override fun onSliderClick(slider: BaseSliderView?) {
        info("slider position: ${ photoSlider.currentPosition }")
        val position = photoSlider.currentPosition
        val intent = GalleryActivity.newInstance(this, ArrayList(urls), position)
        startActivity(intent)
    }
}