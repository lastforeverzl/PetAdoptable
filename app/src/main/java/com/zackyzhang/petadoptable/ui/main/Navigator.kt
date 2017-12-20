package com.zackyzhang.petadoptable.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.favorites.FavoritesFragment
import com.zackyzhang.petadoptable.ui.nearby.NearbyPagerFragment
import com.zackyzhang.petadoptable.ui.shelters.SheltersFragment
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Created by lei on 12/18/17.
 */
class Navigator(private val mainActivity: MainActivity,
                private val fragmentManager: FragmentManager) :
        AnkoLogger {

    var zipCode: String = ""
    private var nearbyPagerFragment = fragmentManager.findFragmentByTag(NearbyPagerFragment::class.java.simpleName)
    private var favoritesFragment = fragmentManager.findFragmentByTag(FavoritesFragment::class.java.simpleName)
    private var shelterFragment = fragmentManager.findFragmentByTag(SheltersFragment::class.java.simpleName)

    fun openNearbyPagerFragment() {
        info(zipCode)
        val ft = fragmentManager.beginTransaction()
        nearbyPagerFragment ?: run {
            info("nearbyPagerFragment is null")
            nearbyPagerFragment = NearbyPagerFragment.newInstance(zipCode)
            ft.add(R.id.contentContainer, nearbyPagerFragment, nearbyPagerFragment::class.java.simpleName)
        }
        nearbyPagerFragment?.let { if (it.isAdded) ft.show(it) }
        favoritesFragment?.let { if (it.isAdded) ft.hide(it) }
        shelterFragment?.let { if (it.isAdded) ft.hide(it) }
        ft.commit()
    }

    fun openFavoritesFragment() {
        info(zipCode)
        val ft = fragmentManager.beginTransaction()
        favoritesFragment ?:run {
            info("favoritesFragment is null")
            favoritesFragment = FavoritesFragment.newInstance()
            ft.add(R.id.contentContainer, favoritesFragment, favoritesFragment::class.java.simpleName)
        }
        favoritesFragment?.let { if (it.isAdded) ft.show(it) }
        nearbyPagerFragment?.let { if (it.isAdded) ft.hide(it) }
        shelterFragment?.let { if (it.isAdded) ft.hide(it) }
        ft.commit()
    }

    fun openSheltersFragment() {
        info(zipCode)
        val ft = fragmentManager.beginTransaction()
        shelterFragment ?:run {
            info("shelterFragment is null")
            shelterFragment = SheltersFragment.newInstance()
            ft.add(R.id.contentContainer, shelterFragment, shelterFragment::class.java.simpleName)
        }
        shelterFragment?.let { if (it.isAdded) ft.show(it) }
        nearbyPagerFragment?.let { if (it.isAdded) ft.hide(it) }
        favoritesFragment?.let { if (it.isAdded) ft.hide(it) }
        ft.commit()
    }

    private fun replaceFragment(fragment: Fragment) {
        info("fragment: " + fragment.toString())
        fragmentManager.beginTransaction()
                .replace(R.id.contentContainer, fragment, fragment::class.java.simpleName)
                .commit()
    }
}
