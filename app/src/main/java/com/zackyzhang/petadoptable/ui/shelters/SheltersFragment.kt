package com.zackyzhang.petadoptable.ui.shelters

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zackyzhang.petadoptable.presentation.browse.BrowseSheltersViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowseSheltersViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.presentation.model.ShelterView
import com.zackyzhang.petadoptable.ui.BuildConfig
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.main.MainActivity
import com.zackyzhang.petadoptable.ui.widget.EndlessRecyclerViewScrollListener
import com.zackyzhang.petadoptable.ui.widget.ShelterOnClickListener
import com.zackyzhang.petadoptable.ui.widget.empty.EmptyListener
import com.zackyzhang.petadoptable.ui.widget.error.ErrorListener
import com.zackyzhang.shelteradoptable.ui.mapper.ShelterMapper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_shelters.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by lei on 12/18/17.
 */
class SheltersFragment : Fragment(), AnkoLogger {

    companion object {

        const val ARG_ZIPCODE = "arg_zipcode"

        fun newInstance(zipCode: String): SheltersFragment {
            val fragment = SheltersFragment()
            val args = Bundle()
            args.putString(ARG_ZIPCODE, zipCode)
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var zipCode: String

    var isLoadingMore = false
    lateinit var linearLayoutManager: LinearLayoutManager
    @Inject lateinit var activity: MainActivity
    @Inject lateinit var sheltersAdapter: SheltersAdapter
    @Inject lateinit var mapper: ShelterMapper
    @Inject lateinit var viewModelFactory: BrowseSheltersViewModelFactory
    private lateinit var browseSheltersViewModel: BrowseSheltersViewModel
    private lateinit var listener: ShelterOnClickListener

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is ShelterOnClickListener) {
            listener = context
        } else {
            throw ClassCastException("${ context.toString() } must implement ShelterOnClickListener.")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zipCode = arguments!!.getString(ARG_ZIPCODE)
        linearLayoutManager = LinearLayoutManager(activity)
        provideViewModel()
        fetchData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shelters, container, false)
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

    fun provideViewModel() {
        browseSheltersViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowseSheltersViewModel::class.java)
    }

    fun viewModelObserve() {
        browseSheltersViewModel.getShelters().observe(this,
                Observer<Resource<List<ShelterView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    fun handleDataState(resourceState: ResourceState, data: List<ShelterView>?,
                        message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> setupScreenForLoadingState()
            ResourceState.SUCCESS -> setupScreenForSuccess(data)
            ResourceState.ERROR -> setupScreenForError(message)
        }
    }

    fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        if (!isLoadingMore) recyclerView.visibility = View.GONE
        viewEmpty.visibility = View.GONE
        viewError.visibility = View.GONE
    }

    fun setupScreenForSuccess(data: List<ShelterView>?) {
        viewError.visibility = View.GONE
        progress.visibility = View.GONE
        if (data != null && data.isNotEmpty()) {
            updateListView(data)
            recyclerView.visibility = View.VISIBLE
        } else {
            viewEmpty.visibility = View.VISIBLE
        }
        isLoadingMore = false
    }

    fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        recyclerView.visibility = View.GONE
        viewEmpty.visibility = View.GONE
        viewError.visibility = View.VISIBLE
        error(message)
    }

    fun fetchData() {
        browseSheltersViewModel.fetchShelters(BuildConfig.PETFINDER_API_KEY, zipCode, sheltersAdapter.itemCount)
    }

    fun updateListView(data: List<ShelterView>) {
        sheltersAdapter.addShelters(data.map { mapper.mapToViewModel(it) })
        sheltersAdapter.notifyDataSetChanged()
        info("Shelters adapter size: ${sheltersAdapter.itemCount}")
    }

    fun setupBrowseRecycler() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        setupAdapter()
        recyclerView.addOnScrollListener(
                object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                        isLoadingMore = true
                        fetchData()
                    }
                })
        recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    fun setupAdapter() {
        sheltersAdapter.shelterOnClicklistener = {
            info("shelter click: ${ it.id } ${ it.name }")
            listener.onClickShelter(it)
        }
        sheltersAdapter.shelterDirectionListener = { lat: String, lng: String, address: String ->
            listener.directToShelter(lat, lng, address)
        }
        sheltersAdapter.shelterCallListener = {
            listener.callShelter(it)
        }
        recyclerView.adapter = sheltersAdapter
    }

    private fun setupViewListeners() {
        viewEmpty.emptyListener = emptyListener
        viewError.errorListener = errorListener
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