package com.zackyzhang.petadoptable.ui.di.module

import com.zackyzhang.petadoptable.ui.di.scopes.PerActivity
import com.zackyzhang.petadoptable.ui.favorites.FavoritesFragmentModule
import com.zackyzhang.petadoptable.ui.main.MainActivity
import com.zackyzhang.petadoptable.ui.main.MainActivityDiModule
import com.zackyzhang.petadoptable.ui.nearby.NearbyPagerFragmentModule
import com.zackyzhang.petadoptable.ui.shelters.SheltersFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by lei on 12/13/17.
 */
@Module
abstract class ActivityBuilder {

//    @PerActivity
//    @ContributesAndroidInjector(modules = arrayOf(BrowseActivityModule::class))
//    abstract fun bindMainActivity(): MainActivityBackup
    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityDiModule::class,
            NearbyPagerFragmentModule::class, SheltersFragmentModule::class, FavoritesFragmentModule::class))
    abstract fun mainActivityInjector(): MainActivity

}