package com.zackyzhang.petadoptable.ui.di.module

import com.zackyzhang.petadoptable.ui.detail.PetDetailActivity
import com.zackyzhang.petadoptable.ui.detail.PetDetailActivityDiModule
import com.zackyzhang.petadoptable.ui.di.scopes.PerActivity
import com.zackyzhang.petadoptable.ui.favorites.FavoritesFragmentModule
import com.zackyzhang.petadoptable.ui.main.MainActivity
import com.zackyzhang.petadoptable.ui.main.MainActivityDiModule
import com.zackyzhang.petadoptable.ui.nearby.NearbyPagerFragmentModule
import com.zackyzhang.petadoptable.ui.search.SearchActivity
import com.zackyzhang.petadoptable.ui.search.SearchActivityDiModule
import com.zackyzhang.petadoptable.ui.search.SearchResultActivity
import com.zackyzhang.petadoptable.ui.search.SearchResultActivityDiModule
import com.zackyzhang.petadoptable.ui.shelterpets.ShelterPetsActivity
import com.zackyzhang.petadoptable.ui.shelterpets.ShelterPetsActivityDiModule
import com.zackyzhang.petadoptable.ui.shelters.SheltersFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by lei on 12/13/17.
 */
@Module
abstract class ActivityBuilder {

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(MainActivityDiModule::class,
            NearbyPagerFragmentModule::class, SheltersFragmentModule::class, FavoritesFragmentModule::class))
    abstract fun mainActivityInjector(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(PetDetailActivityDiModule::class))
    abstract fun petDetailActivityInjector(): PetDetailActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(ShelterPetsActivityDiModule::class))
    abstract fun shelterPetsActivityInjector(): ShelterPetsActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SearchActivityDiModule::class))
    abstract fun searchActivityInjector(): SearchActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(SearchResultActivityDiModule::class))
    abstract fun searchResultActivityInjector(): SearchResultActivity
}