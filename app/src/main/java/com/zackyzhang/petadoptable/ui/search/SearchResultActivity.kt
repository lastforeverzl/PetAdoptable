package com.zackyzhang.petadoptable.ui.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import com.zackyzhang.petadoptable.presentation.browse.BrowseSearchViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowseSearchViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.ui.BuildConfig
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.detail.PetDetailActivity
import com.zackyzhang.petadoptable.ui.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.nearby.AnimalAdapter
import com.zackyzhang.petadoptable.ui.widget.EndlessRecyclerViewScrollListener
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_search_result.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import javax.inject.Inject

class SearchResultActivity : AppCompatActivity(), AnkoLogger {

    companion object {

        val ANIMAL = "SearchResultActivity:animal"
        val BREED = "SearchResultActivity:breed"
        val SEX = "SearchResultActivity:sex"
        val SIZE = "SearchResultActivity:size"
        val AGE = "SearchResultActivity:age"
        val ZIP_CODE = "SearchResultActivity:zipCode"

        fun newInstance(context: Context, zipCode:String, animal: String, breed: String, sex: String,
                        size: String, age: String): Intent {
            val intent = Intent(context, SearchResultActivity::class.java)
            intent.putExtra(ANIMAL, animal)
            intent.putExtra(BREED, breed)
            intent.putExtra(SEX, sex)
            intent.putExtra(SIZE, size)
            intent.putExtra(AGE, age)
            intent.putExtra(ZIP_CODE, zipCode)
            return intent
        }
    }

    lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var zipCode: String
    lateinit var animal: String
    private lateinit var breed: String
    private lateinit var sex: String
    lateinit var size: String
    private lateinit var age: String

    @Inject lateinit var mapper: PetMapper
    @Inject lateinit var browseSearchViewModel: BrowseSearchViewModel
    @Inject lateinit var browseSearchViewModelFactory: BrowseSearchViewModelFactory
    @Inject lateinit var petsAdapter: AnimalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        linearLayoutManager = LinearLayoutManager(this)

        zipCode = intent.getStringExtra(ZIP_CODE)
        animal = intent.getStringExtra(ANIMAL)
        breed = intent.getStringExtra(BREED)
        sex = intent.getStringExtra(SEX)
        size = intent.getStringExtra(SIZE)
        age = intent.getStringExtra(AGE)

        info("$animal,$breed,$sex,$age,$size")


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
        browseSearchViewModel = ViewModelProviders.of(this, browseSearchViewModelFactory)
                .get(BrowseSearchViewModel::class.java)
    }

    private fun fetchData() {
        browseSearchViewModel.fetchPets(
                BuildConfig.PETFINDER_API_KEY, zipCode, animal, breed, sex, size, age, petsAdapter.itemCount)
    }

    private fun setupBrowseRecycler() {
        pets.setHasFixedSize(true)
        pets.layoutManager = linearLayoutManager
        setupAdapter()
        pets.addOnScrollListener(
                object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        fetchData()
                    }
                })
    }

    private fun setupAdapter() {
        petsAdapter.listener = {
            info("animal click: ${ it.id } ${ it.name }")
            val intent = PetDetailActivity.newInstance(this, it.id, it.shelterId)
            startActivity(intent)
        }
        pets.adapter = petsAdapter
    }

    private fun viewModelObserve() {
        browseSearchViewModel.getPetsLiveData().observe(this,
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
        viewError.visibility = View.GONE
    }

    private fun setupScreenForSuccess(data: List<PetView>?) {
        progress.visibility = View.GONE
        viewError.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            pets.visibility = View.VISIBLE
        } else {
            viewEmpty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(data: List<PetView>) {
        petsAdapter.addPets(data.map { mapper.mapToViewModel(it) })
        petsAdapter.notifyDataSetChanged()
        info(animal + " adapter size: " + petsAdapter.itemCount)
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        pets.visibility = View.GONE
        viewEmpty.visibility = View.GONE
        viewError.visibility = View.VISIBLE
        error(message)
    }
}