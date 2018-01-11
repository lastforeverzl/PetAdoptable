package com.zackyzhang.petadoptable.cache.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_BREEDS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_BREED_BY_PET_ID
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_FAVORITE_BREEDS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_FAVORITE_BREED_BY_PET_ID
import com.zackyzhang.petadoptable.cache.db.entity.favorite.FavoriteBreedDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.BreedDbEntity

@Dao
interface BreedDao {

    @Query(QUERY_BREED_BY_PET_ID + ":petId")
    fun getBreedsForPet(petId: Long): List<BreedDbEntity>

    @Query(QUERY_BREEDS)
    fun getAllBreeds(): List<BreedDbEntity>

    @Insert
    fun insertBreeds(breeds: List<BreedDbEntity>)

    @Delete
    fun deleteBreed(breed: BreedDbEntity)

    @Query(QUERY_FAVORITE_BREED_BY_PET_ID + ":petId")
    fun getFavoriteBreedsForPet(petId: Long): List<FavoriteBreedDbEntity>

    @Query(QUERY_FAVORITE_BREEDS)
    fun getAllFavoriteBreeds(): List<FavoriteBreedDbEntity>

    @Insert
    fun insertFavoriteBreeds(breeds: List<FavoriteBreedDbEntity>)

}