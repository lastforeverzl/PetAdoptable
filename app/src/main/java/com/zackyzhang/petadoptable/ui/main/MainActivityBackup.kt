package com.zackyzhang.petadoptable.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetsViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.ui.BuildConfig
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.widget.empty.EmptyListener
import com.zackyzhang.petadoptable.ui.widget.error.ErrorListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_mainbackup.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

class MainActivityBackup : AppCompatActivity(), AnkoLogger {

    @Inject lateinit var browseAdapter: BrowseAdapter
    @Inject lateinit var mapper: PetMapper
    @Inject lateinit var viewModelFactory: BrowsePetsViewModelFactory
    private lateinit var browsePetsViewModel: BrowsePetsViewModel

    private lateinit var zipCode: String

    companion object {
        val ZIP_CODE = "MainActivityBackup:zipCode"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainbackup)
        AndroidInjection.inject(this)

        zipCode = intent.getStringExtra(ZIP_CODE)
        info("zipCode: " + zipCode)

        browsePetsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowsePetsViewModel::class.java)
        info("adapter size: " + browseAdapter.itemCount)
        browsePetsViewModel.fetchPets(BuildConfig.PETFINDER_API_KEY, zipCode, browseAdapter.itemCount, "dog")

        setupBrowseRecycler()
        setupViewListeners()
    }

    override fun onStart() {
        super.onStart()
        browsePetsViewModel.getPets().observe(this,
                Observer<Resource<List<PetView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    private fun setupBrowseRecycler() {
        recycler_browse.layoutManager = LinearLayoutManager(this)
        recycler_browse.adapter = browseAdapter
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
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<PetView>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recycler_browse.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(pets: List<PetView>) {
        browseAdapter.pets = pets.map { mapper.mapToViewModel(it) }
        browseAdapter.notifyDataSetChanged()
        info("adapter size: " + browseAdapter.itemCount)
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recycler_browse.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            browsePetsViewModel.fetchPets(BuildConfig.PETFINDER_API_KEY, zipCode, browseAdapter.itemCount, "dog")
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicker() {
            browsePetsViewModel.fetchPets(BuildConfig.PETFINDER_API_KEY, zipCode, browseAdapter.itemCount, "dog")
        }

    }
}
