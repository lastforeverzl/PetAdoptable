package com.zackyzhang.petadoptable.cache.db.dao

import android.arch.persistence.room.*
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.DELETE_ALL_SHELTERS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_SHELTERS
import com.zackyzhang.petadoptable.cache.db.entity.ShelterDbEntity

@Dao
interface ShelterDao {

    @Query(QUERY_SHELTERS)
    fun getAllShelters(): List<ShelterDbEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShelter(pet: ShelterDbEntity): Long

    @Delete
    fun deleteShelter(pet: ShelterDbEntity)

    @Query(DELETE_ALL_SHELTERS)
    fun clearShelters()

}