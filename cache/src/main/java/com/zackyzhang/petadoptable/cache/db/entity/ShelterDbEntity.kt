package com.zackyzhang.petadoptable.cache.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.SHELTER_TABLE_NAME

@Entity(tableName = SHELTER_TABLE_NAME, indices = arrayOf(Index(value = arrayOf("id"), unique = true)))
data class ShelterDbEntity(
        @PrimaryKey(autoGenerate = true)
        var uid: Long? = null,
        var country: String = "",
        var longitude: String = "",
        var name: String = "",
        var phone: String = "",
        var state: String = "",
        @ColumnInfo(name = "address_two") var address2: String = "",
        var email: String = "",
        var city: String = "",
        var zip: String = "",
        var fax: String = "",
        var latitude: String = "",
        var id: String = "",
        @ColumnInfo(name = "address_one") var address1: String = ""
)