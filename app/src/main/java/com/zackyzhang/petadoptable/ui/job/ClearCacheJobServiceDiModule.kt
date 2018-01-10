package com.zackyzhang.petadoptable.ui.job

import com.zackyzhang.petadoptable.domain.interactor.update.ClearCache
import com.zackyzhang.petadoptable.presentation.update.ClearCacheJob
import com.zackyzhang.petadoptable.ui.di.scopes.PerService
import dagger.Module
import dagger.Provides

/**
 * Created by lei on 1/10/18.
 */
@Module
class ClearCacheJobServiceDiModule {

    @Provides
    @PerService
    fun provideClearCacheJob(clearCache: ClearCache): ClearCacheJob {
        return ClearCacheJob(clearCache)
    }

}