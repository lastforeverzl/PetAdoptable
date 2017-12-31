package com.zackyzhang.petadoptable.ui.shelters

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import com.zackyzhang.petadoptable.presentation.browse.BrowseSheltersViewModel
import com.zackyzhang.petadoptable.presentation.browse.BrowseSheltersViewModelFactory
import com.zackyzhang.petadoptable.presentation.data.Resource
import com.zackyzhang.petadoptable.presentation.model.ShelterView
import com.zackyzhang.petadoptable.ui.BaseFragment
import com.zackyzhang.petadoptable.ui.BuildConfig
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.main.MainActivity
import com.zackyzhang.shelteradoptable.ui.mapper.ShelterMapper
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_shelters.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by lei on 12/18/17.
 */
class SheltersFragment : BaseFragment<ShelterView>(), AnkoLogger {

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

    @Inject lateinit var activity: MainActivity
    @Inject lateinit var sheltersAdapter: SheltersAdapter
    @Inject lateinit var mapper: ShelterMapper
    @Inject lateinit var viewModelFactory: BrowseSheltersViewModelFactory
    private lateinit var browseSheltersViewModel: BrowseSheltersViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        zipCode = arguments!!.getString(ARG_ZIPCODE)
        super.onCreate(savedInstanceState)

    }

    override fun provideViewModel() {
        browseSheltersViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(BrowseSheltersViewModel::class.java)
    }

    override fun viewModelObserve() {
        browseSheltersViewModel.getShelters().observe(this,
                Observer<Resource<List<ShelterView>>> {
                    if (it != null) this.handleDataState(it.status, it.data, it.message)
                })
    }

    override fun fetchData() {
        browseSheltersViewModel.fetchShelters(BuildConfig.PETFINDER_API_KEY, zipCode, sheltersAdapter.itemCount)
    }

    override fun updateListView(data: List<ShelterView>) {
        sheltersAdapter.addShelters(data.map { mapper.mapToViewModel(it) })
        sheltersAdapter.notifyDataSetChanged()
        info("Shelters adapter size: ${sheltersAdapter.itemCount}")
    }

    override fun getLayout(): Int {
        return R.layout.fragment_shelters
    }

    override fun setupAdapter() {
        recyclerView.adapter = sheltersAdapter
    }

    override fun onDestroy() {
        info("onDestroy")
        super.onDestroy()
    }
}