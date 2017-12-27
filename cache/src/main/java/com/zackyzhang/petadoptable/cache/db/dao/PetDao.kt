package com.zackyzhang.petadoptable.cache.db.dao

import android.arch.persistence.room.*
import com.zackyzhang.petadoptable.cache.db.constants.DbConstans.DELETE_ALL_PETS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstans.QUERY_FAVORITE_PETS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstans.QUERY_PETS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstans.QUERY_PETS_BY_ANIMAL
import com.zackyzhang.petadoptable.cache.db.entity.PetDbEntity

/**
 * Created by lei on 12/6/17.
 */
@Dao
interface PetDao {

    @Query(QUERY_PETS)
    fun getAllPets(): List<PetDbEntity>

    @Query(QUERY_PETS_BY_ANIMAL + ":animal COLLATE NOCASE")
    fun getAllPetsByAnimal(animal: String): List<PetDbEntity>

    @Query(QUERY_FAVORITE_PETS)
    fun getFavoritePets(): List<PetDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPet(pet: PetDbEntity): Long

    @Delete
    fun deletePet(pet: PetDbEntity)

    @Query(DELETE_ALL_PETS)
    fun clearPets()

}