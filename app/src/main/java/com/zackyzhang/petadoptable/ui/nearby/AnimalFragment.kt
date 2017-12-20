package com.zackyzhang.petadoptable.ui.nearby

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetsViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.ui.BuildConfig
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.mapper.PetMapper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_animal.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by lei on 12/18/17.
 */
class AnimalFragment : Fragment(), AnkoLogger {

    companion object {

        const val ARG_ANIMAL = "arg_animal"
        const val ARG_ZIPCODE = "arg_zipcode"

        fun newInstance(animal: String, zipCode: String): AnimalFragment {
            val fragment = AnimalFragment()
            val args = Bundle()
            args.putString(ARG_ANIMAL, animal)
            args.putString(ARG_ZIPCODE, zipCode)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var zipCode: String
    lateinit var animal: String

    @Inject lateinit var animalAdapter: AnimalAdapter
    @Inject lateinit var mapper: PetMapper
    @Inject lateinit var viewModelFactory: BrowsePetsViewModelFactory
    private lateinit var browsePetsViewModel: BrowsePetsViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zipCode = arguments!!.getString(ARG_ZIPCODE)
        animal = arguments!!.getString(ARG_ANIMAL)

        info(animal + " " + zipCode)

        browsePetsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowsePetsViewModel::class.java)
        info("adapter size: " + animalAdapter.itemCount)
        browsePetsViewModel.fetchPets(BuildConfig.PETFINDER_API_KEY, zipCode, animalAdapter.itemCount, animal)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_animal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        noPetsFound.visibility = View.VISIBLE
        noPetsFound.text = animal + " " + zipCode

        setupBrowseRecycler()
//        setupViewListeners()
        browsePetsViewModel.getPets().observe(this,
                Observer<Resource<List<PetView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    private fun setupBrowseRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = animalAdapter
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
        recyclerView.visibility = View.GONE
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
            viewEmpty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(pets: List<PetView>) {
        animalAdapter.pets = pets.map { mapper.mapToViewModel(it) }
        animalAdapter.notifyDataSetChanged()
        info("adapter size: " + animalAdapter.itemCount)
    }

    private fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recyclerView.visibility = View.GONE
        viewEmpty.visibility = View.GONE
        viewError.visibility = View.VISIBLE
    }
}