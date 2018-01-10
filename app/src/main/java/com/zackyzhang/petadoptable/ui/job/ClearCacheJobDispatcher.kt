package com.zackyzhang.petadoptable.ui.job

import android.content.Context
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Lifetime
import com.firebase.jobdispatcher.Trigger
import java.util.concurrent.TimeUnit

/**
 * Created by lei on 1/10/18.
 */
class ClearCacheJobDispatcher {

    companion object {

        private val JOB_INTERVAL_HOURS = 24
        private val JOB_INTERVAL_SECONDS = TimeUnit.HOURS.toSeconds(JOB_INTERVAL_HOURS.toLong()).toInt()
        private val JOB_FLEXTIME_SECONDS = TimeUnit.HOURS.toSeconds(1).toInt()

        private val CLEAR_CACHE_TAG = "ClearCacheJobDispatcher:clearCacheJob"

        @Synchronized
        fun scheduleFirebaseJobDispatcher(context: Context) {
            val driver = GooglePlayDriver(context)
            val dispatcher = FirebaseJobDispatcher(driver)

            val clearCacheJob = dispatcher.newJobBuilder()
                    .setService(ClearCacheJobService::class.java)
                    .setTag(CLEAR_CACHE_TAG)
                    .setRecurring(true)
                    .setLifetime(Lifetime.FOREVER)
                    .setTrigger(Trigger.executionWindow(
                        JOB_INTERVAL_SECONDS,
                        JOB_INTERVAL_SECONDS + JOB_FLEXTIME_SECONDS))
//                    .setTrigger(Trigger.executionWindow(0, 60)) // For test purpose.
                    .setReplaceCurrent(true)
                    .build()
            dispatcher.schedule(clearCacheJob)
        }
    }

}