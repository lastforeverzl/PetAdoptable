package com.zackyzhang.petadoptable.ui.di.component

import android.app.Application
import com.zackyzhang.petadoptable.ui.PetAdoptableApplication
import com.zackyzhang.petadoptable.ui.di.module.ActivityBuilder
import com.zackyzhang.petadoptable.ui.di.module.ApplicationModule
import com.zackyzhang.petadoptable.ui.di.scopes.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * Created by lei on 12/13/17.
 */
@PerApplication
@Component(modules = arrayOf(ActivityBuilder::class, ApplicationModule::class,
        AndroidSupportInjectionModule::class))
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: PetAdoptableApplication)
}