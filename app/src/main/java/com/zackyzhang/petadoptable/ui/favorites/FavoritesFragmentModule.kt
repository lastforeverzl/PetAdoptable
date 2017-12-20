package com.zackyzhang.petadoptable.ui.favorites

import com.zackyzhang.petadoptable.ui.di.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by lei on 12/18/17.
 */
//@Module(includes = arrayOf(BaseFragmentModule::class))
@Module
abstract class FavoritesFragmentModule {

//    @Binds
//    @Named(BaseFragmentModule.FRAGMENT)
//    @PerFragment
//    abstract fun fragment(favoritesFragment: FavoritesFragment): Fragment
    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(FavoritesFragmentDiModule::class))
    abstract fun bindFavoritesFragment(): FavoritesFragment

}