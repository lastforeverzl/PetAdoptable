package com.zackyzhang.petadoptable.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.zackyzhang.petadoptable.cache.db.dao.BreedDao
import com.zackyzhang.petadoptable.cache.db.dao.MediaDao
import com.zackyzhang.petadoptable.cache.db.dao.PetDao
import com.zackyzhang.petadoptable.cache.db.entity.BreedDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.MediaDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.PetDbEntity

/**
 * Created by lei on 12/6/17.
 */
@Database(entities = arrayOf(PetDbEntity::class, MediaDbEntity::class, BreedDbEntity::class),
        version = 1)
abstract class PetAdoptableDatabase : RoomDatabase() {
    abstract fun getPetDao(): PetDao

    abstract fun getMediaDao(): MediaDao

    abstract fun getBreedDao(): BreedDao

    companion object {

        fun getInstance(context: Context): PetAdoptableDatabase =
                Room.databaseBuilder(context,
                        PetAdoptableDatabase::class.java, "petadoptable-db")
                        .build()
    }
}