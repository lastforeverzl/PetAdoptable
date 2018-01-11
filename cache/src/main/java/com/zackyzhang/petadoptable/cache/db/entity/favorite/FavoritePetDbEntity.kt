package com.zackyzhang.petadoptable.cache.db.entity.favorite

import android.arch.persistence.room.*
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.FAVORITE_PET_TABLE_NAME

@Entity(tableName = FAVORITE_PET_TABLE_NAME, indices = arrayOf(Index(value = arrayOf("id"), unique = true)))
data class FavoritePetDbEntity(
        @PrimaryKey(autoGenerate = true) var uid: Long? = null,
        var status: String = "",
        @ColumnInfo(name = "city_state") var cityState: String = "",
        var age: String = "",
        var size: String = "",
        @Ignore var medias: List<String> = mutableListOf(),
        var id: String = "",
        @Ignore var breeds: List<String> = mutableListOf(),
        var name: String = "",
        var sex: String = "",
        var description: String = "",
        var mix: String = "",
        @ColumnInfo(name = "shelter_id") var shelterId: String = "",
        var lastUpdate: String = "",
        var animal: String = ""
)