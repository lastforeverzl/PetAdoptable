package com.zackyzhang.petadoptable.ui.main

import android.support.v4.app.FragmentManager
import com.zackyzhang.petadoptable.ui.di.scopes.PerActivity
import dagger.Module
import dagger.Provides
import javax.inject.Named

/**
 * Created by lei on 12/18/17.
 */
@Module
class MainActivityDiModule {

    companion object {
        const val FRAGMENT_MANAGER = "MainActivityDiModule.fragmentManager"
    }

    @Provides
    @PerActivity
    fun provideNavigator(activity: MainActivity, @Named(FRAGMENT_MANAGER)fragmentManager: FragmentManager):
            Navigator {
        return Navigator(activity, fragmentManager)
    }

    @Provides
    @Named(FRAGMENT_MANAGER)
    @PerActivity
    fun provideActivityFragmentManager(activity: MainActivity): FragmentManager {
        return activity.supportFragmentManager
    }


}