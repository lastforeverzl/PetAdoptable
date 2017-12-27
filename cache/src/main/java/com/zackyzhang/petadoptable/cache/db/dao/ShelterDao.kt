package com.zackyzhang.petadoptable.cache.db.dao

import android.arch.persistence.room.*
import com.zackyzhang.petadoptable.cache.db.constants.DbConstans.DELETE_ALL_SHELTERS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstans.QUERY_SHELTERS
import com.zackyzhang.petadoptable.cache.db.entity.ShelterDbEntity

/**
 * Created by lei on 12/21/17.
 */
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