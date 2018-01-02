package com.zackyzhang.petadoptable.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zackyzhang.petadoptable.presentation.data.ResourceState
import com.zackyzhang.petadoptable.ui.widget.EndlessRecyclerViewScrollListener
import com.zackyzhang.petadoptable.ui.widget.empty.EmptyListener
import com.zackyzhang.petadoptable.ui.widget.error.ErrorListener
import kotlinx.android.synthetic.main.fragment_animal.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

/**
 * Created by lei on 12/22/17.
 */
abstract class BaseFragment<in T> : Fragment(), AnkoLogger {

    var isLoadingMore = false
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupBrowseRecycler()
        setupViewListeners()
        viewModelObserve()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayoutManager = LinearLayoutManager(activity)
        provideViewModel()
        fetchData()
    }

    open fun setupBrowseRecycler() {
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
    }

    fun handleDataState(resourceState: ResourceState, data: List<T>?,
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

    fun setupScreenForSuccess(data: List<T>?) {
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

    abstract fun provideViewModel()

    abstract fun viewModelObserve()

    abstract fun fetchData()

    abstract fun updateListView(data: List<T>)

    abstract fun getLayout(): Int

    abstract fun setupAdapter()

}