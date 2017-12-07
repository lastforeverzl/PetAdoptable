package com.zackyzhang.petadoptable.cache.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.zackyzhang.petadoptable.cache.db.constants.DbConstans.QUERY_BREEDS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstans.QUERY_BREED_BY_PET_ID
import com.zackyzhang.petadoptable.cache.db.entity.BreedDbEntity

/**
 * Created by lei on 12/6/17.
 */
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

}