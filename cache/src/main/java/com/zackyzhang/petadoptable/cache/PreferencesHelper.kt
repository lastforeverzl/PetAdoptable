package com.zackyzhang.petadoptable.cache

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(context: Context) {

    companion object {
        private val PREF_PET_PACKAGE_NAME = "com.zackyzhang.petadoptable.preferences"

        private val PREF_KEY_PETS_LAST_CACHE = "pets_last_cache"

        private val PREF_KEY_SHELTERS_LAST_CACHE = "shelters_last_cache"
    }

    private val petPref: SharedPreferences

    init {
        petPref = context.getSharedPreferences(PREF_PET_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    /**
     * Store and retrieve the last time pets data was created
     */
    var petsLastCacheTime: Long
        get() = petPref.getLong(PREF_KEY_PETS_LAST_CACHE, 0)
        set(lastCache) = petPref.edit().putLong(PREF_KEY_PETS_LAST_CACHE, lastCache).apply()

    var sheltersLastCacheTime: Long
        get() = petPref.getLong(PREF_KEY_SHELTERS_LAST_CACHE, 0)
        set(lastCache) = petPref.edit().putLong(PREF_KEY_SHELTERS_LAST_CACHE, lastCache).apply()
}