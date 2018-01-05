package com.zackyzhang.petadoptable.cache.db.entity.petcache

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.zackyzhang.petadoptable.cache.db.constants.DbConstans.FAVORITE_BREED_TABLE_NAME

/**
 * Created by lei on 12/6/17.
 */
@Entity(tableName = FAVORITE_BREED_TABLE_NAME, foreignKeys = arrayOf(ForeignKey(entity = FavoritePetDbEntity::class,
        parentColumns = arrayOf("uid"),
        childColumns = arrayOf("pet_id"),
        onDelete = ForeignKey.CASCADE)))
data class FavoriteBreedDbEntity(
        @PrimaryKey(autoGenerate = true)
        var uid: Long? = null,
        @ColumnInfo(name = "pet_id")
        var petId: Long = 0L,
        var value: String = ""
)