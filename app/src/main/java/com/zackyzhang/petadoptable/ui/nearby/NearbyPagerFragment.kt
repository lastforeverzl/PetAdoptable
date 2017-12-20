package com.zackyzhang.petadoptable.ui.nearby

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zackyzhang.petadoptable.ui.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_viewpager.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject



/**
 * Created by lei on 12/16/17.
 */
class NearbyPagerFragment : Fragment(), HasSupportFragmentInjector, AnkoLogger {

    companion object {

        const val ARG_ZIPCODE = "arg_zipcode"

        fun newInstance(zipCode: String): NearbyPagerFragment {
            val fragment = NearbyPagerFragment()
            val args = Bundle()
            args.putString(ARG_ZIPCODE, zipCode)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var zipCode: String

    @Inject lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var nearbyPagerAdapter: NearbyPagerAdapter

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zipCode = arguments!!.getString(ARG_ZIPCODE)

        info("activity: " + activity.toString())
        info(zipCode)
        nearbyPagerAdapter.zipCode = zipCode
        nearbyPagerAdapter.animalType = activity!!.resources.getStringArray(R.array.animals).toList()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_viewpager, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewpager.adapter = nearbyPagerAdapter
        slidingTabs.setupWithViewPager(viewpager)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    override fun onDestroy() {
        info("onDestroy")
        super.onDestroy()
    }
}