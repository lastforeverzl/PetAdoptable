package com.zackyzhang.petadoptable.cache

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by lei on 12/6/17.
 */
@Singleton
class PreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private val PREF_PET_PACKAGE_NAME = "com.zackyzhang.petadoptable.preferences"

        private val PREF_KEY_LAST_CACHE = "last_cache"
        private val PREF_KEY_IS_CACHED = "is_cached"
    }

    private val petPref: SharedPreferences

    init {
        petPref = context.getSharedPreferences(PREF_PET_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time data was created
     */
    var lastCacheTime: Long
        get() = petPref.getLong(PREF_KEY_LAST_CACHE, 0)
        set(lastCache) = petPref.edit().putLong(PREF_KEY_LAST_CACHE, lastCache).apply()

    var isCached: Boolean
        get() = petPref.getBoolean(PREF_KEY_IS_CACHED, false)
        set(isCached) = petPref.edit().putBoolean(PREF_KEY_IS_CACHED, isCached).apply()
}