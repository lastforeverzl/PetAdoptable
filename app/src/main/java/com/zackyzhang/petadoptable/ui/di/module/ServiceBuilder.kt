package com.zackyzhang.petadoptable.ui.di.module

import com.zackyzhang.petadoptable.ui.di.scopes.PerService
import com.zackyzhang.petadoptable.ui.job.ClearCacheJobService
import com.zackyzhang.petadoptable.ui.job.ClearCacheJobServiceDiModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceBuilder {

    @PerService
    @ContributesAndroidInjector(modules = arrayOf(ClearCacheJobServiceDiModule::class))
    abstract fun clearCacheServiceInjector(): ClearCacheJobService

}