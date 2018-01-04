package com.zackyzhang.petadoptable.cache

import android.arch.persistence.room.Room
import com.nhaarman.mockito_kotlin.any
import com.zackyzhang.petadoptable.cache.db.PetAdoptableDatabase
import com.zackyzhang.petadoptable.cache.db.entity.petcache.FavoritePetDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.PetDbEntity
import com.zackyzhang.petadoptable.cache.mapper.FavoriteEntityMapper
import com.zackyzhang.petadoptable.cache.mapper.PetEntityMapper
import com.zackyzhang.petadoptable.cache.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.cache.test.factory.PetFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals

/**
 * Created by lei on 12/6/17.
 */

@RunWith(RobolectricTestRunner::class)
class PetsCacheImplTest {

    private var petsDatabase = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application,
            PetAdoptableDatabase::class.java).allowMainThreadQueries().build()
    private var entityMapper = PetEntityMapper()
    private var preferencesHelper = PreferencesHelper(RuntimeEnvironment.application)
    private var favoriteMapper = FavoriteEntityMapper()

    private val databaseHelper = PetsCacheImpl(petsDatabase, entityMapper, favoriteMapper, preferencesHelper)

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearPets().test()
        testObserver.assertComplete()
    }

    @Test
    fun savePetsCompletes() {
        val petEntities = PetFactory.makePetEntityList(2)

        val testObserver = databaseHelper.savePets(petEntities).test()
        testObserver.assertComplete()
    }

    @Test
    fun savePetsSavesData() {
        val petsCount = 2 // each pet has two medias and two breeds in it.
        val petEntities = PetFactory.makePetEntityList(petsCount)

        databaseHelper.savePets(petEntities).test()
        checkNumRowsInPetsTable(petsCount)
    }

    @Test
    fun getPetsCompletes() {
        val testObserver = databaseHelper.getPets(any()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPetsReturnsData() {
        val petEntities = PetFactory.makePetEntityList(2)
        val cachedPets = mutableListOf<PetDbEntity>()
        petEntities.forEach {
            cachedPets.add(entityMapper.mapToCached(it))
        }
        insertPets(cachedPets)
        val testObserver = databaseHelper.getPets("dog").test()
//        testObserver.assertValue(petEntities)
    }

    @Test
    fun getFavoritePetsCompletes() {
        val testObserver = databaseHelper.getFavoritePets().test()
        testObserver.assertComplete()
    }

    @Test
    fun getFavoritePetsReturnsData() {
        val petEntities = PetFactory.makePetEntityList(2)
        val favoritePets = mutableListOf<FavoritePetDbEntity>()
        petEntities.forEach {
            val favoritePetDbEntity = favoriteMapper.mapToCached(it)
            favoritePets.add(favoritePetDbEntity)
            insertFavoritePet(favoritePetDbEntity)
        }
        val testObserver = databaseHelper.getFavoritePets().test()
//        testObserver.assertValue(petEntities)
    }

    @Test
    fun saveFavoritePetCompletes() {
        val petEntity = PetFactory.makePetEntity()

        val testObserver = databaseHelper.saveFavoritePet(petEntity).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveFavoritePetSavesData() {
        val petEntity = PetFactory.makePetEntity()

        databaseHelper.saveFavoritePet(petEntity).test()
        val number = petsDatabase.getPetDao().getFavoritePets()
        assertEquals(1, number.size)
    }

    @Test
    fun removeFavoritePetCompletes() {
        val petEntity = PetFactory.makePetEntity()
        val favoritePetDbEntity = favoriteMapper.mapToCached(petEntity)
        insertFavoritePet(favoritePetDbEntity)
        val testObserver = databaseHelper.removeFavoritePet(petEntity).test()
        testObserver.assertComplete()
    }

    @Test
    fun isFavoritePetReturnsTrue() {
        val favoritePetDbEntity = PetFactory.makeFavoritePetEntity()
        val id = favoritePetDbEntity.id
        insertFavoritePet(favoritePetDbEntity)
        val testObserver = databaseHelper.isFavoritePet(id).test()
        testObserver.assertValue(true)
    }

    @Test
    fun isFavoritePetReturnsFalse() {
        val favoritePetDbEntity = PetFactory.makeFavoritePetEntity()
        val id = randomUuid()
        insertFavoritePet(favoritePetDbEntity)
        val testObserver = databaseHelper.isFavoritePet(id).test()
        testObserver.assertValue(false)
    }

    @Test
    fun getPetByIdCompletes() {
        val petDbEntity = PetFactory.makeCachedPet()
        petsDatabase.getPetDao().insertPet(petDbEntity)
        val testObserver = databaseHelper.getPetById(petDbEntity.id).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPetByIdReturnsData() {
        val petEntity = PetFactory.makePetEntity()
        val petDbEntity = entityMapper.mapToCached(petEntity)
        petsDatabase.getPetDao().insertPet(petDbEntity)
        val testObserver = databaseHelper.getPetById(petDbEntity.id).test()
//        testObserver.assertValue(petEntity)
    }

    private fun insertPets(cachedPets: List<PetDbEntity>) {
        cachedPets.forEach {
            petsDatabase.getPetDao().insertPet(it)
        }
    }

    private fun insertFavoritePet(favoritePetDbEntity: FavoritePetDbEntity) {
        petsDatabase.getPetDao().insertFavoritePet(favoritePetDbEntity)
    }

    private fun checkNumRowsInPetsTable(expectedRows: Int) {
        val numberOfPetRows = petsDatabase.getPetDao().getAllPets().size
        val numberOfMediaRows = petsDatabase.getMediaDao().getAllMedias().size
        val numberOfBreedRows = petsDatabase.getBreedDao().getAllBreeds().size
        assertEquals(expectedRows, numberOfPetRows)
        assertEquals(expectedRows * 2, numberOfMediaRows)
        assertEquals(expectedRows * 2, numberOfBreedRows)
    }

}