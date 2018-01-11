package com.zackyzhang.petadoptable.ui.nearby

import android.support.v4.app.FragmentManager
import com.zackyzhang.petadoptable.ui.di.scopes.PerFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class NearbyPagerFragmentDiModule {

    companion object {
        const val CHILD_FRAGMENT_MANAGER = "NearbyPagerFragmentDiModule.childFragmentManager"
    }

    @Provides
    @Named(CHILD_FRAGMENT_MANAGER)
    @PerFragment
    fun provideChildFragmentManager(fragment: NearbyPagerFragment): FragmentManager {
        return fragment.childFragmentManager
    }

    @Provides
    @PerFragment
    fun provideNearbyPagerAdapter(@Named(CHILD_FRAGMENT_MANAGER)childFragmentManager: FragmentManager) :
            NearbyPagerAdapter {
        return NearbyPagerAdapter(childFragmentManager)
    }

}