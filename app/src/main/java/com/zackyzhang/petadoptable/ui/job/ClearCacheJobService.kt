package com.zackyzhang.petadoptable.ui.job

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.zackyzhang.petadoptable.presentation.update.ClearCacheJob
import dagger.android.AndroidInjection
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by lei on 1/9/18.
 */
class ClearCacheJobService : JobService(), AnkoLogger {

    @Inject lateinit var clearCacheJob: ClearCacheJob

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onStartJob(job: JobParameters?): Boolean {
        info("ClearCacheJobService onStartJob")
        clearCacheJob.clearCache()
        return true
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return true
    }

}