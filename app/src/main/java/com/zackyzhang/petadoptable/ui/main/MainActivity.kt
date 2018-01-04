package com.zackyzhang.petadoptable.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.zackyzhang.petadoptable.ui.R
import com.zackyzhang.petadoptable.ui.favorites.FavoritesFragment
import com.zackyzhang.petadoptable.ui.model.PetViewModel
import com.zackyzhang.petadoptable.ui.widget.PetOnClickListener
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by lei on 12/16/17.
 */
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, AnkoLogger, PetOnClickListener {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var zipCode: String

    companion object {
        val ZIP_CODE = "MainActivity:zipCode"
        const val PET_DETAIL_ACTIVITY_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        zipCode = intent.getStringExtra(ZIP_CODE)
        info("zipCode: " + zipCode)
        navigator.zipCode = zipCode
        bottomNavigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigator.openNearbyPagerFragment()
    }

    override fun onDestroy() {
        info("onDestroy")
        super.onDestroy()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    private val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.tab_nearby -> {
                        navigator.openNearbyPagerFragment()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.tab_favorites -> {
                        navigator.openFavoritesFragment()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.tab_shelters -> {
                        navigator.openSheltersFragment()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }

    override fun onPetClick(pet: PetViewModel) {
        info("animal click: ${ pet.id } ${ pet.name }")
        navigator.openDetailActivity(pet.id, pet.shelterId)
    }

    /**
     * Avoid fragments overlapping
     */
    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PET_DETAIL_ACTIVITY_REQUEST) {
            val fragment = supportFragmentManager
                    .findFragmentByTag(FavoritesFragment::class.java.simpleName) as FavoritesFragment
            fragment?.let { it.fetchData() }
        }
    }
}