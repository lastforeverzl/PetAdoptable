package com.zackyzhang.petadoptable.ui.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zackyzhang.petadoptable.presentation.browse.BrowseFavoritePetsViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowseFavoritePetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.nearby.AnimalAdapter
import com.zackyzhang.petadoptable.ui.widget.PetOnClickListener
import com.zackyzhang.petadoptable.ui.widget.empty.EmptyListener
import com.zackyzhang.petadoptable.ui.widget.error.ErrorListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import javax.inject.Inject

class FavoritesFragment : Fragment(), AnkoLogger {

    companion object {
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }

    private lateinit var linearLayoutManager: LinearLayoutManager
    @Inject lateinit var favoritePetsAdapter: AnimalAdapter
    @Inject lateinit var mapper: PetMapper
    @Inject lateinit var viewModelFactory: BrowseFavoritePetsViewModelFactory
    private lateinit var browseFavoritePetsViewModel: BrowseFavoritePetsViewModel

    private lateinit var listener: PetOnClickListener

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is PetOnClickListener) {
            listener = context
        } else {
            throw ClassCastException("${ context.toString() } must implement PetOnClickListener.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        provideViewModel()
        fetchData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupBrowseRecycler()
        setupViewListeners()
        viewModelObserve()
    }

    override fun onDestroy() {
        info("onDestroy")
        super.onDestroy()
    }

    private fun provideViewModel() {
        browseFavoritePetsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowseFavoritePetsViewModel::class.java)
    }

    private fun viewModelObserve() {
        browseFavoritePetsViewModel.getFavoritePetsLiveData().observe(this,
                Observer<Resource<List<PetView>>>{
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
        viewError.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recyclerView.visibility = View.VISIBLE
        } else {
            favoritePetsAdapter.clearAdapter()
            favoritePetsAdapter.notifyDataSetChanged()
            viewEmpty.visibility = View.VISIBLE
        }
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recyclerView.visibility = View.GONE
        viewEmpty.visibility = View.GONE
        viewError.visibility = View.VISIBLE
        error(message)
    }

    private fun setupViewListeners() {
        viewEmpty.emptyListener = emptyListener
        viewError.errorListener = errorListener
    }

    private fun setupBrowseRecycler() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        setupAdapter()
    }

    fun fetchData() {
        browseFavoritePetsViewModel.fetchFavoritePets()
    }

    private fun updateListView(data: List<PetView>) {
        favoritePetsAdapter.clearAdapter()
        favoritePetsAdapter.addPets(data.map { mapper.mapToViewModel(it) })
        favoritePetsAdapter.notifyDataSetChanged()
    }

    private fun setupAdapter() {
        favoritePetsAdapter.listener = {
            info("animal click: ${ it.id } ${ it.name }")
            listener.onPetClick(it)
        }
        recyclerView.adapter = favoritePetsAdapter
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            fetchData()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicker() {
            fetchData()
        }
    }

}