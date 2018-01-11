package com.zackyzhang.petadoptable.ui.shelterpets

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.zackyzhang.petadoptable.presentation.browse.BrowseShelterPetsViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowseShelterPetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.ui.BuildConfig
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.detail.PetDetailActivity
import com.zackyzhang.petadoptable.ui.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.widget.EndlessRecyclerViewScrollListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_shelterpets.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import javax.inject.Inject

class ShelterPetsActivity : AppCompatActivity(), AnkoLogger {

    companion object {
        val SHELTER_ID = "ShelterPetsActivity:shelterId"
        val SHELTER_NAME = "ShelterPetsActivity:shelterName"
        val SHELTER_PHONE = "ShelterPetsActivity:shelterPhone"
        val SHELTER_EMAIL = "ShelterPetsActivity:shelterEmail"
        val SHELTER_LAT = "ShelterPetsActivity:shelterLat"
        val SHELTER_LNG = "ShelterPetsActivity:shelterLng"
        val SHELTER_ADDRESS = "ShelterPetsActivity:shelterAddress"

        fun newInstance(context: Context, id: String, name: String, phone: String,
                        email: String, lat: String, lng: String, address: String): Intent {
            val intent = Intent(context, ShelterPetsActivity::class.java)
            intent.putExtra(SHELTER_ID, id)
            intent.putExtra(SHELTER_NAME, name)
            intent.putExtra(SHELTER_PHONE, phone)
            intent.putExtra(SHELTER_EMAIL, email)
            intent.putExtra(SHELTER_LAT, lat)
            intent.putExtra(SHELTER_LNG, lng)
            intent.putExtra(SHELTER_ADDRESS, address)
            return intent
        }
    }

    private lateinit var shelterId: String
    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var shelterName: String
    private lateinit var shelterPhone: String
    lateinit var shelterEmail: String
    private lateinit var shelterLat: String
    private lateinit var shelterLng: String
    private lateinit var shelterAddress: String

    @Inject lateinit var browseShelterPetsModelFactory: BrowseShelterPetsViewModelFactory
    @Inject lateinit var mapper: PetMapper
    @Inject lateinit var shelterPetsAdapter: ShelterPetsAdapter
    private lateinit var browseShelterPetsModel: BrowseShelterPetsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shelterpets)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        linearLayoutManager = LinearLayoutManager(this)

        shelterId = intent.getStringExtra(SHELTER_ID)
        shelterName = intent.getStringExtra(SHELTER_NAME)
        shelterPhone = intent.getStringExtra(SHELTER_PHONE)
        shelterEmail = intent.getStringExtra(SHELTER_EMAIL)
        shelterLat = intent.getStringExtra(SHELTER_LAT)
        shelterLng = intent.getStringExtra(SHELTER_LNG)
        shelterAddress = intent.getStringExtra(SHELTER_ADDRESS)

        supportActionBar!!.title = shelterName

        provideViewModel()
        fetchData()
        setupBrowseRecycler()
        viewModelObserve()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun provideViewModel() {
        browseShelterPetsModel = ViewModelProviders.of(this, browseShelterPetsModelFactory)
                .get(BrowseShelterPetsViewModel::class.java)
    }

    fun fetchData() {
        browseShelterPetsModel.fetchPets(BuildConfig.PETFINDER_API_KEY, shelterId, shelterPetsAdapter.itemCount)
    }

    private fun setupBrowseRecycler() {
        rvShelterPets.setHasFixedSize(true)
        rvShelterPets.layoutManager = linearLayoutManager
        setupAdapter()
        rvShelterPets.addOnScrollListener(
                object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        fetchData()
                    }
                })
    }

    private fun viewModelObserve() {
        browseShelterPetsModel.getPetsLiveData().observe(this,
                Observer<Resource<List<PetView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    private fun handleDataState(resourceState: ResourceState, data: List<PetView>?,
                        message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
        }
    }

    private fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        viewEmpty.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<PetView>?) {
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            rvShelterPets.visibility = View.VISIBLE
        } else {
            viewEmpty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(data: List<PetView>) {
        shelterPetsAdapter.addPets(data.map { mapper.mapToViewModel(it) })
        shelterPetsAdapter.notifyDataSetChanged()
        info("adapter size: ${ shelterPetsAdapter.itemCount }")
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        viewEmpty.visibility = View.GONE
        error(message)
    }

    private fun setupAdapter() {
        shelterPetsAdapter.petOnClickListener = {
            info("animal click: ${ it.id } ${ it.name }")
            val intent = PetDetailActivity.newInstance(this, it.id, shelterId)
            startActivity(intent)
        }
        shelterPetsAdapter.shelterOnCallListener = {
            val call = Uri.parse("tel: ${ shelterPhone.trim() }")
            val intent = Intent(Intent.ACTION_DIAL, call)
            startActivity(intent)
        }
        shelterPetsAdapter.shelterOnEmailListener = {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:")
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(shelterEmail))
            intent.resolveActivity(packageManager)?.let { startActivity(intent) }
        }
        shelterPetsAdapter.shelterOnDirectionListener = {
            val gmmIntentUri = Uri.parse("geo:$shelterLat,$shelterLng?q=$shelterAddress")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package` = "com.google.android.apps.maps"
            startActivity(mapIntent)
        }
        shelterPetsAdapter.setHeader(shelterPhone, shelterEmail, shelterLat, shelterLng, shelterAddress)
        rvShelterPets.adapter = shelterPetsAdapter
    }

}