package com.zackyzhang.petadoptable.cache.dao

import android.arch.persistence.room.Room
import com.zackyzhang.petadoptable.cache.db.PetAdoptableDatabase
import com.zackyzhang.petadoptable.cache.test.factory.PetFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Created by lei on 12/6/17.
 */
@RunWith(RobolectricTestRunner::class)
open class PetDaoTest {

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
    fun insertPetSavesData() {
        val petDbEntity = PetFactory.makeCachedPet()
        petAdoptableDatabase.getPetDao().insertPet(petDbEntity)

        val pets = petAdoptableDatabase.getPetDao().getAllPets()
        assert(pets.isNotEmpty())
    }

    @Test
    fun getAllPetsRetrievesData() {
        val cachedPets = PetFactory.makeCachedPetList(5)

        cachedPets.forEach {
            petAdoptableDatabase.getPetDao().insertPet(it)
        }

        val retrievePets = petAdoptableDatabase.getPetDao().getAllPets()
        assert(retrievePets == cachedPets.sortedWith(compareBy({ it.uid }, { it.uid })))
    }

    @Test
    fun updatePetSavesData() {
        val petDbEntity = PetFactory.makeCachedPet()

        val uid = petAdoptableDatabase.getPetDao().insertPet(petDbEntity)

        val updated = PetFactory.makeFavoritePet(uid)
        val count = petAdoptableDatabase.getPetDao().updatePet(updated)
        println("updated rows: $count")
        val pets = petAdoptableDatabase.getPetDao().getFavoritePets()

        assert(pets.isNotEmpty())
    }

    @Test
    fun getFavoritePetsRetrievesData() {
        val cachedPets = PetFactory.makeFavoritePetList(5)

        cachedPets.forEach {
            petAdoptableDatabase.getPetDao().insertPet(it)
        }

        val retrievePets = petAdoptableDatabase.getPetDao().getFavoritePets()
        assert(retrievePets == cachedPets.sortedWith(compareBy({ it.uid }, { it.uid })))
    }

    @Test
    fun getPetByIdRetrievesData() {
        val petDbEntity = PetFactory.makeFavoritePet(anyLong())
        petAdoptableDatabase.getPetDao().insertPet(petDbEntity)
        val retrievePet = petAdoptableDatabase.getPetDao().getPetById(petDbEntity.id)
        assert(retrievePet == petDbEntity)
    }
}