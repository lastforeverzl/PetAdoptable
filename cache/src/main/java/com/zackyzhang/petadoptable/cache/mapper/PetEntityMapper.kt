package com.zackyzhang.petadoptable.cache.mapper

import com.zackyzhang.petadoptable.cache.db.entity.petcache.BreedDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.MediaDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.PetDbEntity
import com.zackyzhang.petadoptable.data.model.PetEntity
import javax.inject.Inject

/**
 * Map a [PetDbEntity] instance to and from a [PetEntity] instance when data is moving between
 * this layer and the Data layer
 */
class PetEntityMapper @Inject constructor(): EntityMapper<PetDbEntity, PetEntity> {

    /**
     * Map a [PetEntity] instance to a [PetDbEntity] instance
     */
    override fun mapToCached(type: PetEntity): PetDbEntity {
        return PetDbEntity(null, type.status, type.cityState, type.age, type.size, type.medias,
                type.id, type.breeds, type.name, type.sex, type.description, type.mix, type.shelterId,
                type.lastUpdate, type.animal, type.isFavorite)
    }

    override fun mapFromCached(type: PetDbEntity): PetEntity {
        return PetEntity(type.status, type.cityState, type.age, type.size, type.medias, type.id,
                type.breeds, type.name, type.sex, type.description, type.mix, type.shelterId,
                type.lastUpdate, type.animal, type.isFavorite)
    }

    fun mapToCachedMedia(type: List<String>, rowId: Long): List<MediaDbEntity> {
        val mediaList = mutableListOf<MediaDbEntity>()
        type.forEach { media -> mediaList.add(MediaDbEntity(null, rowId, media)) }
        return mediaList
    }

    fun mapFromCachedMedia(type: List<MediaDbEntity>): List<String> {
        val list = mutableListOf<String>()
        type.forEach { media -> list.add(media.value) }
        return list
    }

    fun mapToCachedBreed(type: List<String>, rowId: Long): List<BreedDbEntity> {
        val breedList = mutableListOf<BreedDbEntity>()
        type.forEach { breed -> breedList.add(BreedDbEntity(null, rowId, breed)) }
        return breedList
    }

    fun mapFromCachedBreed(type: List<BreedDbEntity>): List<String> {
        val list = mutableListOf<String>()
        type.forEach { breed -> list.add(breed.value) }
        return list
    }

}
