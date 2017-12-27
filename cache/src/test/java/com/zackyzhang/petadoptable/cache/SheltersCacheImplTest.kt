package com.zackyzhang.shelteradoptable.cache

import android.arch.persistence.room.Room
import com.zackyzhang.petadoptable.cache.PreferencesHelper
import com.zackyzhang.petadoptable.cache.SheltersCacheImpl
import com.zackyzhang.petadoptable.cache.db.PetAdoptableDatabase
import com.zackyzhang.petadoptable.cache.db.entity.ShelterDbEntity
import com.zackyzhang.petadoptable.cache.mapper.ShelterEntityMapper
import com.zackyzhang.petadoptable.cache.test.factory.ShelterFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals

/**
 * Created by lei on 12/22/17.
 */
@RunWith(RobolectricTestRunner::class)
class SheltersCacheImplTest {

    private var sheltersDatabase = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application,
            PetAdoptableDatabase::class.java).allowMainThreadQueries().build()
    private var entityMapper = ShelterEntityMapper()
    private var preferencesHelper = PreferencesHelper(RuntimeEnvironment.application)

    private val databaseHelper = SheltersCacheImpl(sheltersDatabase, entityMapper, preferencesHelper)

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearShelters().test()
        testObserver.assertComplete()
    }

    @Test
    fun saveSheltersCompletes() {
        val shelterEntities = ShelterFactory.makeShelterEntityList(2)

        val testObserver = databaseHelper.saveShelters(shelterEntities).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveSheltersSavesData() {
        val sheltersCount = 2
        val shelterEntities = ShelterFactory.makeShelterEntityList(sheltersCount)

        databaseHelper.saveShelters(shelterEntities).test()
        checkNumRowsInSheltersTable(sheltersCount)
    }

    @Test
    fun getSheltersCompletes() {
        val testObserver = databaseHelper.getShelters().test()
        testObserver.assertComplete()
    }

    @Test
    fun getSheltersReturnsData() {
        val shelterEntities = ShelterFactory.makeShelterEntityList(2)
        val cachedShelters = mutableListOf<ShelterDbEntity>()
        shelterEntities.forEach {
            cachedShelters.add(entityMapper.mapToCached(it))
        }
        insertShelters(cachedShelters)
    }

    private fun insertShelters(cachedShelters: List<ShelterDbEntity>) {
        cachedShelters.forEach {
            sheltersDatabase.getShelterDao().insertShelter(it)
        }
    }

    private fun checkNumRowsInSheltersTable(expectedRows: Int) {
        val numberOfShelterRows = sheltersDatabase.getShelterDao().getAllShelters().size
        assertEquals(expectedRows, numberOfShelterRows)
    }
}