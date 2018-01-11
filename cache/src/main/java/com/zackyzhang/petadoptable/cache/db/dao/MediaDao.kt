package com.zackyzhang.petadoptable.cache.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_FAVORITE_MEDIAS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_FAVORITE_MEDIA_BY_PET_ID
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_MEDIAS
import com.zackyzhang.petadoptable.cache.db.constants.DbConstants.QUERY_MEDIA_BY_PET_ID
import com.zackyzhang.petadoptable.cache.db.entity.favorite.FavoriteMediaDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.MediaDbEntity

@Dao
interface MediaDao {

    @Query(QUERY_MEDIA_BY_PET_ID + ":petId")
    fun getMediasForPet(petId: Long): List<MediaDbEntity>

    @Query(QUERY_MEDIAS)
    fun getAllMedias(): List<MediaDbEntity>

    @Insert
    fun insertMedias(medias: List<MediaDbEntity>)

    @Delete
    fun deleteMedia(media: MediaDbEntity)

    @Query(QUERY_FAVORITE_MEDIA_BY_PET_ID + ":petId")
    fun getFavoriteMediasForPet(petId: Long): List<FavoriteMediaDbEntity>

    @Query(QUERY_FAVORITE_MEDIAS)
    fun getAllFavoriteMedias(): List<FavoriteMediaDbEntity>

    @Insert
    fun insertFavoriteMedias(medias: List<FavoriteMediaDbEntity>)

}