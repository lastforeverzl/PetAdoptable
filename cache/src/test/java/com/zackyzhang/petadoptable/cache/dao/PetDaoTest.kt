package com.zackyzhang.petadoptable.cache.dao

import android.arch.persistence.room.Room
import com.zackyzhang.petadoptable.cache.db.PetAdoptableDatabase
import com.zackyzhang.petadoptable.cache.test.factory.PetFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
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
    fun insertFavoritePetSavesData() {
        val favoritePetDbEntity = PetFactory.makeFavoritePetEntity()
        petAdoptableDatabase.getPetDao().insertFavoritePet(favoritePetDbEntity)

        val favoritePets = petAdoptableDatabase.getPetDao().getFavoritePets()
        assert(favoritePets.isNotEmpty())
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
    fun getAllPetsByAnimalRetrievesData() {
        val cachedPets = PetFactory.makeCachedPetList(5)
        cachedPets.forEach {
            petAdoptableDatabase.getPetDao().insertPet(it)
        }
        val animal = cachedPets[0].animal
        val retrievePets = petAdoptableDatabase.getPetDao().getAllPetsByAnimal(animal)
        assert(retrievePets[0] == cachedPets[0])
    }

    @Test
    fun getFavoritePetsRetrievesData() {
        val favoritePets = PetFactory.makeFavoritePetEntityList(5)

        favoritePets.forEach {
            petAdoptableDatabase.getPetDao().insertFavoritePet(it)
        }

        val retrieveFavoritePets = petAdoptableDatabase.getPetDao().getFavoritePets()
        assert(retrieveFavoritePets == favoritePets.sortedWith(compareBy({ it.uid }, { it.uid })))
    }

    @Test
    fun getPetByIdRetrievesData() {
        val petDbEntity = PetFactory.makeCachedPet()
        petAdoptableDatabase.getPetDao().insertPet(petDbEntity)
        val retrievePet = petAdoptableDatabase.getPetDao().getPetById(petDbEntity.id)
        assert(retrievePet == petDbEntity)
    }

    @Test
    fun getFavoritePetByIdRetrievesData() {
        val favoritePetDbEntity = PetFactory.makeFavoritePetEntity()
        petAdoptableDatabase.getPetDao().insertFavoritePet(favoritePetDbEntity)
        val retrieveFavoritePet = petAdoptableDatabase.getPetDao().getFavoritePetById(favoritePetDbEntity.id)
        assert(retrieveFavoritePet == favoritePetDbEntity)
    }

    @Test
    fun removeFavoritePetCompletes() {
        val favoritePetDbEntity = PetFactory.makeFavoritePetEntity()
        val uid = petAdoptableDatabase.getPetDao().insertFavoritePet(favoritePetDbEntity)
        favoritePetDbEntity.uid = uid
        petAdoptableDatabase.getPetDao().removeFavoritePet(favoritePetDbEntity)
        val retrieveFavoritePet = petAdoptableDatabase.getPetDao().getFavoritePetById(favoritePetDbEntity.id)
        assert(retrieveFavoritePet == null)
    }
}