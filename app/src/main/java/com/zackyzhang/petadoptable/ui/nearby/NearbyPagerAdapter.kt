package com.zackyzhang.petadoptable.ui.nearby

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by lei on 12/19/17.
 */
class NearbyPagerAdapter constructor(childFragmentManager: FragmentManager) :
        FragmentPagerAdapter(childFragmentManager), AnkoLogger {

    var zipCode: String = ""
    var animalType: List<String> = mutableListOf()

    override fun getItem(position: Int): Fragment {
        info(zipCode)
        return AnimalFragment.newInstance(animalType[position], zipCode)
    }

    override fun getCount(): Int {
        return animalType.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return animalType[position]
    }
}