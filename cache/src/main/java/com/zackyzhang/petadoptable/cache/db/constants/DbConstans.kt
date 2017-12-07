package com.zackyzhang.petadoptable.cache.db.constants

/**
 * Created by lei on 12/6/17.
 */
object DbConstans {

    const val PET_TABLE_NAME = "pet"

    const val MEDIA_TABLE_NAME = "media"

    const val BREED_TABLE_NAME = "breed"

    const val QUERY_PETS = "SELECT * FROM $PET_TABLE_NAME"

    const val QUERY_MEDIAS = "SELECT * FROM $MEDIA_TABLE_NAME"

    const val QUERY_BREEDS = "SELECT * FROM $BREED_TABLE_NAME"

    const val QUERY_MEDIA_BY_PET_ID = "SELECT * FROM $MEDIA_TABLE_NAME WHERE pet_id = "

    const val QUERY_BREED_BY_PET_ID = "SELECT * FROM $BREED_TABLE_NAME WHERE pet_id = "

    const val DELETE_ALL_PETS = "DELETE FROM $PET_TABLE_NAME"
}