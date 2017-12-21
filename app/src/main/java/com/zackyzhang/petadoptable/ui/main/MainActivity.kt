package com.zackyzhang.petadoptable.ui.main

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.zackyzhang.petadoptable.ui.R
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
class MainActivity : AppCompatActivity(), HasSupportFragmentInjector, AnkoLogger {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var zipCode: String

    companion object {
        val ZIP_CODE = "MainActivity:zipCode"
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

    /**
     * Avoid fragments overlapping
     */
    override fun onSaveInstanceState(outState: Bundle?) {
//        super.onSaveInstanceState(outState)
    }
}