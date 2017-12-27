package com.zackyzhang.petadoptable.ui.di.module

import android.app.Application
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.zackyzhang.petadoptable.cache.PetsCacheImpl
import com.zackyzhang.petadoptable.cache.PreferencesHelper
import com.zackyzhang.petadoptable.cache.SheltersCacheImpl
import com.zackyzhang.petadoptable.cache.db.PetAdoptableDatabase
import com.zackyzhang.petadoptable.cache.mapper.PetEntityMapper
import com.zackyzhang.petadoptable.cache.mapper.ShelterEntityMapper
import com.zackyzhang.petadoptable.data.PetsDataRepository
import com.zackyzhang.petadoptable.data.SheltersDataRepository
import com.zackyzhang.petadoptable.data.executor.JobExecutor
import com.zackyzhang.petadoptable.data.mapper.PetMapper
import com.zackyzhang.petadoptable.data.mapper.ShelterMapper
import com.zackyzhang.petadoptable.data.repository.PetsCache
import com.zackyzhang.petadoptable.data.repository.PetsRemote
import com.zackyzhang.petadoptable.data.repository.SheltersCache
import com.zackyzhang.petadoptable.data.repository.SheltersRemote
import com.zackyzhang.petadoptable.data.source.PetsDataStoreFactory
import com.zackyzhang.petadoptable.data.source.SheltersDataStoreFactory
import com.zackyzhang.petadoptable.domain.executor.PostExecutionThread
import com.zackyzhang.petadoptable.domain.executor.ThreadExecutor
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import com.zackyzhang.petadoptable.domain.repository.SheltersRepository
import com.zackyzhang.petadoptable.remote.PetFinderService
import com.zackyzhang.petadoptable.remote.PetFinderServiceFactory
import com.zackyzhang.petadoptable.remote.impl.PetsRemoteImpl
import com.zackyzhang.petadoptable.remote.impl.SheltersRemoteImpl
import com.zackyzhang.petadoptable.ui.BuildConfig
import com.zackyzhang.petadoptable.ui.UiThread
import com.zackyzhang.petadoptable.ui.di.scopes.PerApplication
import dagger.Module
import dagger.Provides

/**
 * Module used to provide dependencies at an application-level.
 */
@Module
open class ApplicationModule {

    @Provides
    @PerApplication
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @PerApplication
    internal fun providePreferencesHelper(context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }

    @Provides
    @PerApplication
    internal fun providePetRepository(factory: PetsDataStoreFactory,
                                      mapper: PetMapper): PetsRepository {
        return PetsDataRepository(factory, mapper)
    }

    @Provides
    @PerApplication
    internal fun provideShelterRepository(factory: SheltersDataStoreFactory,
                                          mapper: ShelterMapper): SheltersRepository {
        return SheltersDataRepository(factory, mapper)
    }

    @Provides
    @PerApplication
    internal fun providePetCache(database: PetAdoptableDatabase,
                                 entityMapper: PetEntityMapper,
                                 helper: PreferencesHelper): PetsCache {
        return PetsCacheImpl(database, entityMapper, helper)
    }

    @Provides
    @PerApplication
    internal fun provideShelterCache(database: PetAdoptableDatabase,
                                     entityMapper: ShelterEntityMapper,
                                     helper: PreferencesHelper): SheltersCache {
        return SheltersCacheImpl(database, entityMapper, helper)
    }

    @Provides
    @PerApplication
    internal fun providePetRemote(service: PetFinderService,
                                  entityMapper: com.zackyzhang.petadoptable.remote.mapper.PetEntityMapper): PetsRemote {
        return PetsRemoteImpl(service, entityMapper)
    }

    @Provides
    @PerApplication
    internal fun provideShelterRemote(service: PetFinderService,
                                  entityMapper: com.zackyzhang.petadoptable.remote.mapper.ShelterEntityMapper): SheltersRemote {
        return SheltersRemoteImpl(service, entityMapper)
    }

    @Provides
    @PerApplication
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor {
        return jobExecutor
    }

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    @PerApplication
    internal fun providePetFinderServie(): PetFinderService {
        return PetFinderServiceFactory.makePetFinderService(BuildConfig.DEBUG)
    }

    @Provides
    @PerApplication
    internal fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelProvider.NewInstanceFactory()
    }

    @Provides
    @PerApplication
    internal fun providePetAdoptableDatabase(application: Application): PetAdoptableDatabase {
        return PetAdoptableDatabase.getInstance(application)
    }
}
