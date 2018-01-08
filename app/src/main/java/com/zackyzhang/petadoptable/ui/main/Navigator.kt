package com.zackyzhang.petadoptable.ui.main

import android.app.ActivityOptions
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.View
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.detail.PetDetailActivity
import com.zackyzhang.petadoptable.ui.favorites.FavoritesFragment
import com.zackyzhang.petadoptable.ui.nearby.NearbyPagerFragment
import com.zackyzhang.petadoptable.ui.search.SearchActivity
import com.zackyzhang.petadoptable.ui.shelterpets.ShelterPetsActivity
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
            shelterFragment = SheltersFragment.newInstance(zipCode)
            ft.add(R.id.contentContainer, shelterFragment, shelterFragment::class.java.simpleName)
        }
        shelterFragment?.let { if (it.isAdded) ft.show(it) }
        nearbyPagerFragment?.let { if (it.isAdded) ft.hide(it) }
        favoritesFragment?.let { if (it.isAdded) ft.hide(it) }
        ft.commit()
    }

    fun openDetailActivity(petId: String, shelterId: String) {
        val intent = PetDetailActivity.newInstance(mainActivity, petId, shelterId)
        mainActivity.startActivityForResult(intent, MainActivity.PET_DETAIL_ACTIVITY_REQUEST)
    }

    fun openShelterPetsActivity(shelterId: String, shelterName: String, shelterPhone: String,
                                shelterEmail: String, shelterLat: String, shelterLng: String,
                                shelterAddress: String) {
        val intent = ShelterPetsActivity.newInstance(mainActivity, shelterId, shelterName,
                shelterPhone, shelterEmail, shelterLat, shelterLng, shelterAddress)
        mainActivity.startActivityForResult(intent, MainActivity.SHELTER_PETS_ACTIVITY_REQUEST)
    }

    fun openSearchActivity(searchView: View, zipCode: String) {
        val intent = SearchActivity.newInstance(mainActivity, zipCode)
        val bundle: Bundle
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bundle = ActivityOptions.makeSceneTransitionAnimation(mainActivity, searchView,
                    mainActivity.getString(R.string.transition_search_back)).toBundle()
            mainActivity.startActivity(intent, bundle)
        } else {
            mainActivity.startActivity(intent)
        }
    }

    fun openActionDialIntent(number: String) {
        val call = Uri.parse("tel: ${ number.trim() }")
        val intent = Intent(Intent.ACTION_DIAL, call)
        mainActivity.startActivity(intent)
    }

    fun openDirectionIntent(lat: String, lng: String, address: String) {
        val gmmIntentUri = Uri.parse("geo:$lat,$lng?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.`package` = "com.google.android.apps.maps"
        mainActivity.startActivity(mapIntent)
    }

}
