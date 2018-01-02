package com.zackyzhang.petadoptable.ui.nearby

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetsViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowsePetsViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.ui.BaseFragment
import com.zackyzhang.petadoptable.ui.BuildConfig
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.mapper.PetMapper
import com.zackyzhang.petadoptable.ui.widget.PetOnClickListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_animal.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by lei on 12/18/17.
 */
class AnimalFragment : BaseFragment<PetView>(), AnkoLogger {

    companion object {

        const val ARG_ANIMAL = "arg_animal"
        const val ARG_ZIPCODE = "arg_zipcode"
        const val RV_POSITION = "recyclerView_last_position"

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
    private var rvPosition = 0

    @Inject lateinit var animalAdapter: AnimalAdapter
    @Inject lateinit var mapper: PetMapper
    @Inject lateinit var viewModelFactory: BrowsePetsViewModelFactory
    private lateinit var browsePetsViewModel: BrowsePetsViewModel
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
        zipCode = arguments!!.getString(ARG_ZIPCODE)
        animal = arguments!!.getString(ARG_ANIMAL)
        super.onCreate(savedInstanceState)
//        info("adapter size: " + animalAdapter.itemCount)
    }

    override fun provideViewModel() {
        browsePetsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowsePetsViewModel::class.java)
    }

    override fun viewModelObserve() {
        browsePetsViewModel.getPetsLiveData().observe(this,
                Observer<Resource<List<PetView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    override fun fetchData() {
        browsePetsViewModel.fetchPets(BuildConfig.PETFINDER_API_KEY, zipCode, animalAdapter.itemCount, animal)
    }

    override fun getLayout(): Int {
        return R.layout.fragment_animal
    }

    override fun setupAdapter() {
        animalAdapter.listener = {
            info("animal click: ${ it.id } ${ it.name }")
            listener.onPetClick(it)
        }
        recyclerView.adapter = animalAdapter
    }

    override fun updateListView(data: List<PetView>) {
//        animalAdapter.pets = pets.map { mapper.mapToViewModel(it) }
        animalAdapter.addPets(data.map { mapper.mapToViewModel(it) })
        animalAdapter.notifyDataSetChanged()
        info(animal + " adapter size: " + animalAdapter.itemCount)
        if (!isLoadingMore) recyclerView.scrollToPosition(rvPosition)
    }

    override fun onDestroy() {
        info(animal + " onDestroy")
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val rvPosition = Integer.toString(linearLayoutManager.findFirstVisibleItemPosition())
        info(animal + " save position: " + rvPosition)
        outState.putString(RV_POSITION, rvPosition)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedInstanceState?.let {
            info(animal + " restore position: " + it.getString(RV_POSITION))
            rvPosition = it.getString(RV_POSITION).toInt()
        }
    }
}