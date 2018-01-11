package com.zackyzhang.petadoptable.cache.dao

import android.arch.persistence.room.Room
import com.zackyzhang.petadoptable.cache.db.PetAdoptableDatabase
import com.zackyzhang.petadoptable.cache.test.factory.ShelterFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
open class ShelterDaoTest {

    private lateinit var petAdoptableDatabase: PetAdoptableDatabase

    @Before
    fun initDb() {
        petAdoptableDatabase = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.baseContext,
                PetAdoptableDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        petAdoptableDatabase.close()
    }

    @Test
    fun insertShelterSavesData() {
        val shelterDbEntity = ShelterFactory.makeCachedShelter()
        petAdoptableDatabase.getShelterDao().insertShelter(shelterDbEntity)

        val shelters = petAdoptableDatabase.getShelterDao().getAllShelters()
        assert(shelters.isNotEmpty())
    }

    @Test
    fun getAllSheltersRetrievesData() {
        val cachedShelters = ShelterFactory.makeCachedShelterList(5)

        cachedShelters.forEach {
            petAdoptableDatabase.getShelterDao().insertShelter(it)
        }

        val retrieveShelters = petAdoptableDatabase.getShelterDao().getAllShelters()
        assert(retrieveShelters == cachedShelters.sortedWith(compareBy({ it.uid }, { it.uid })))
    }

}