package com.zackyzhang.petadoptable.ui.nearby

import com.zackyzhang.petadoptable.ui.di.scopes.PerChildFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by lei on 12/18/17.
 */
//@Module(includes = arrayOf(BaseChildFragmentModule::class))
@Module
abstract class AnimalFragmentModule {

//    @Binds
//    @Named(BaseChildFragmentModule.CHILD_FRAGMENT)
//    @PerChildFragment
//    abstract fun fragment(animalFragment: AnimalFragment): Fragment

    @PerChildFragment
    @ContributesAndroidInjector(modules = arrayOf(AnimalFragmentDiModule::class))
    abstract fun bindAnimalFragment(): AnimalFragment
}