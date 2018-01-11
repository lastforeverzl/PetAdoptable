package com.zackyzhang.petadoptable.cache.db.constants

object DbConstants {

    const val PET_TABLE_NAME = "pet"

    const val FAVORITE_PET_TABLE_NAME = "favorite_pet"

    const val SHELTER_TABLE_NAME = "shelter"

    const val MEDIA_TABLE_NAME = "media"

    const val BREED_TABLE_NAME = "breed"

    const val FAVORITE_MEDIA_TABLE_NAME = "favorite_media"

    const val FAVORITE_BREED_TABLE_NAME = "favorite_breed"

    const val QUERY_PETS = "SELECT * FROM $PET_TABLE_NAME"

    const val QUERY_PETS_BY_ANIMAL = "SELECT * FROM $PET_TABLE_NAME WHERE animal = "

    const val QUERY_FAVORITE_PETS = "SELECT * FROM $FAVORITE_PET_TABLE_NAME"

    const val QUERY_PET_BY_ID = "SELECT * FROM $PET_TABLE_NAME WHERE id = "

    const val QUERY_FAVORITE_PET_BY_ID = "SELECT * FROM $FAVORITE_PET_TABLE_NAME WHERE id = "

    const val QUERY_MEDIAS = "SELECT * FROM $MEDIA_TABLE_NAME"

    const val QUERY_BREEDS = "SELECT * FROM $BREED_TABLE_NAME"

    const val QUERY_FAVORITE_MEDIAS = "SELECT * FROM $FAVORITE_MEDIA_TABLE_NAME"

    const val QUERY_FAVORITE_BREEDS = "SELECT * FROM $FAVORITE_BREED_TABLE_NAME"

    const val QUERY_MEDIA_BY_PET_ID = "SELECT * FROM $MEDIA_TABLE_NAME WHERE pet_id = "

    const val QUERY_FAVORITE_MEDIA_BY_PET_ID = "SELECT * FROM $FAVORITE_MEDIA_TABLE_NAME WHERE pet_id = "

    const val QUERY_BREED_BY_PET_ID = "SELECT * FROM $BREED_TABLE_NAME WHERE pet_id = "

    const val QUERY_FAVORITE_BREED_BY_PET_ID = "SELECT * FROM $FAVORITE_BREED_TABLE_NAME WHERE pet_id = "

    const val QUERY_SHELTERS = "SELECT * FROM $SHELTER_TABLE_NAME"

    const val DELETE_ALL_PETS = "DELETE FROM $PET_TABLE_NAME"

    const val DELETE_ALL_SHELTERS = "DELETE FROM $SHELTER_TABLE_NAME"
}