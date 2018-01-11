package com.zackyzhang.petadoptable.cache.db.dao

import android.arch.persistence.room.*
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.DELETE_ALL_PETS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_FAVORITE_PETS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_FAVORITE_PET_BY_ID
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_PETS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_PETS_BY_ANIMAL
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_PET_BY_ID
import com.zackyzhang.petadoptable.cache.db.entity.favorite.FavoritePetDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.PetDbEntity

@Dao
interface PetDao {

    @Query(QUERY_PETS)
    fun getAllPets(): List<PetDbEntity>

    @Query(QUERY_PETS_BY_ANIMAL + ":animal COLLATE NOCASE")
    fun getAllPetsByAnimal(animal: String): List<PetDbEntity>

    @Query(QUERY_FAVORITE_PETS)
    fun getFavoritePets(): List<FavoritePetDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPet(pet: PetDbEntity): Long

//    @Update
//    fun updatePet(pet: PetDbEntity): Int

    @Query(QUERY_PET_BY_ID + ":id")
    fun getPetById(id: String): PetDbEntity

    @Delete
    fun deletePet(pet: PetDbEntity)

    @Query(DELETE_ALL_PETS)
    fun clearPets()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoritePet(favoritePet: FavoritePetDbEntity): Long

    @Query(QUERY_FAVORITE_PET_BY_ID + ":id")
    fun getFavoritePetById(id: String): FavoritePetDbEntity

    @Delete
    fun removeFavoritePet(favoritePet: FavoritePetDbEntity)

}