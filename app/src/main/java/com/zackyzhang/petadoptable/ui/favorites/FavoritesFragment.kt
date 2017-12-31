package com.zackyzhang.petadoptable.ui.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import com.zackyzhang.petadoptable.presentation.browse.BrowseFavoritePetsViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowseFavoritePetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.ui.BaseFragment
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.nearby.AnimalAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by lei on 12/18/17.
 */
class FavoritesFragment : BaseFragment<PetView>(), AnkoLogger {

    companion object {
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }

    @Inject lateinit var favoritePetsAdapter: AnimalAdapter
    @Inject lateinit var mapper: PetMapper
    @Inject lateinit var viewModelFactory: BrowseFavoritePetsViewModelFactory
    private lateinit var browseFavoritePetsViewModel: BrowseFavoritePetsViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun provideViewModel() {
        browseFavoritePetsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowseFavoritePetsViewModel::class.java)
    }

    override fun viewModelObserve() {
        browseFavoritePetsViewModel.getFavoritePetsLiveData().observe(this,
                Observer<Resource<List<PetView>>>{
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    override fun fetchData() {
        browseFavoritePetsViewModel.fetchFavoritePets()
    }

    override fun updateListView(data: List<PetView>) {
        favoritePetsAdapter.addPets(data.map { mapper.mapToViewModel(it) })
        favoritePetsAdapter.notifyDataSetChanged()
    }

    override fun getLayout(): Int {
        return R.layout.fragment_favorites
    }

    override fun setupAdapter() {
        recyclerView.adapter = favoritePetsAdapter
    }

    override fun onDestroy() {
        info("onDestroy")
        super.onDestroy()
    }
}