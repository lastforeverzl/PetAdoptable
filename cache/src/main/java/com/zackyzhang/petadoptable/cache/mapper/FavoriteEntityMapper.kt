package com.zackyzhang.petadoptable.cache.mapper

import com.zackyzhang.petadoptable.cache.db.entity.petcache.FavoriteBreedDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.FavoriteMediaDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.FavoritePetDbEntity
import com.zackyzhang.petadoptable.data.model.PetEntity
import javax.inject.Inject

/**
 * Created by lei on 1/2/18.
 */
class FavoriteEntityMapper @Inject constructor(): EntityMapper<FavoritePetDbEntity, PetEntity> {

    /**
     * Map a [PetEntity] instance to a [CachedPet] instance
     */
    override fun mapToCached(type: PetEntity): FavoritePetDbEntity {
        return FavoritePetDbEntity(null, type.status, type.cityState, type.age, type.size, type.medias,
                type.id, type.breeds, type.name, type.sex, type.description, type.mix, type.shelterId,
                type.lastUpdate, type.animal)
    }

    override fun mapFromCached(type: FavoritePetDbEntity): PetEntity {
        return PetEntity(type.status, type.cityState, type.age, type.size, type.medias, type.id,
                type.breeds, type.name, type.sex, type.description, type.mix, type.shelterId,
                type.lastUpdate, type.animal)
    }

    fun mapToCachedMedia(type: List<String>, rowId: Long): List<FavoriteMediaDbEntity> {
        val mediaList = mutableListOf<FavoriteMediaDbEntity>()
        type.forEach { media -> mediaList.add(FavoriteMediaDbEntity(null, rowId, media)) }
        return mediaList
    }

    fun mapFromCachedMedia(type: List<FavoriteMediaDbEntity>): List<String> {
        val list = mutableListOf<String>()
        type.forEach { media -> list.add(media.value) }
        return list
    }

    fun mapToCachedBreed(type: List<String>, rowId: Long): List<FavoriteBreedDbEntity> {
        val breedList = mutableListOf<FavoriteBreedDbEntity>()
        type.forEach { breed -> breedList.add(FavoriteBreedDbEntity(null, rowId, breed)) }
        return breedList
    }

    fun mapFromCachedBreed(type: List<FavoriteBreedDbEntity>): List<String> {
        val list = mutableListOf<String>()
        type.forEach { breed -> list.add(breed.value) }
        return list
    }

}