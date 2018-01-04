package com.zackyzhang.petadoptable.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.zackyzhang.petadoptable.cache.db.dao.BreedDao
import com.zackyzhang.petadoptable.cache.db.dao.MediaDao
import com.zackyzhang.petadoptable.cache.db.dao.PetDao
import com.zackyzhang.petadoptable.cache.db.dao.ShelterDao
import com.zackyzhang.petadoptable.cache.db.entity.ShelterDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.*

/**
 * Created by lei on 12/6/17.
 */
@Database(entities = arrayOf(PetDbEntity::class, MediaDbEntity::class, BreedDbEntity::class,
        ShelterDbEntity::class, FavoritePetDbEntity::class, FavoriteMediaDbEntity::class,
        FavoriteBreedDbEntity::class), version = 1)
abstract class PetAdoptableDatabase : RoomDatabase() {
    abstract fun getPetDao(): PetDao

    abstract fun getMediaDao(): MediaDao

    abstract fun getBreedDao(): BreedDao

    abstract fun getShelterDao(): ShelterDao

    companion object {

        fun getInstance(context: Context): PetAdoptableDatabase =
                Room.databaseBuilder(context,
                        PetAdoptableDatabase::class.java, "petadoptable-db")
//                        .allowMainThreadQueries()
                        .build()
    }
}