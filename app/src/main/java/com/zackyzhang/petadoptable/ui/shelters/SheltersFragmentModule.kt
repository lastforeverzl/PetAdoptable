package com.zackyzhang.petadoptable.ui.shelters

import com.zackyzhang.petadoptable.ui.di.scopes.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

//@Module(includes = arrayOf(BaseFragmentModule::class))
@Module
abstract class SheltersFragmentModule {

//    @Binds
//    @Named(BaseFragmentModule.FRAGMENT)
//    @PerFragment
//    abstract fun fragment(sheltersFragment: SheltersFragment): Fragment

    @PerFragment
    @ContributesAndroidInjector(modules = arrayOf(SheltersFragmentDiModule::class))
    abstract fun bindSheltersFragment(): SheltersFragment

}