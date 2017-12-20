package com.zackyzhang.petadoptable.ui.nearby

import com.zackyzhang.petadoptable.ui.di.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by lei on 12/18/17.
 */
//@Module(includes = arrayOf(BaseFragmentModule::class))
@Module
abstract class NearbyPagerFragmentModule {

//    @PerChildFragment
//    @ContributesAndroidInjector(modules = arrayOf(AnimalFragmentModule::class))
//    abstract fun animalFragmentInjector(): AnimalFragment
//
//    @Binds
//    @Named(BaseFragmentModule.FRAGMENT)
//    @PerFragment
//    abstract fun fragment(nearbyPagerFragment: NearbyPagerFragment): Fragment

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(NearbyPagerFragmentDiModule::class,
            AnimalFragmentModule::class))
    abstract fun bindNearbyPagerFragment(): NearbyPagerFragment

}