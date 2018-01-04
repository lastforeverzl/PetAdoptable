package com.zackyzhang.petadoptable.cache.mapper

import com.zackyzhang.petadoptable.cache.db.entity.petcache.BreedDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.MediaDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.petcache.PetDbEntity
import com.zackyzhang.petadoptable.cache.test.factory.DataFactory.Factory.randomLong
import com.zackyzhang.petadoptable.cache.test.factory.PetFactory
import com.zackyzhang.petadoptable.data.model.PetEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

/**
 * Created by lei on 12/6/17.
 */
@RunWith(JUnit4::class)
class PetEntityMapperTest {

    private lateinit var petEntityMapper: PetEntityMapper

    @Before
    fun setUp() {
        petEntityMapper = PetEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val petEntity = PetFactory.makePetEntity()
        val cachedPet = petEntityMapper.mapToCached(petEntity)

        assertPetDataEquality(petEntity, cachedPet)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedPet = PetFactory.makeCachedPet()
        val petEntity = petEntityMapper.mapFromCached(cachedPet)

        assertPetDataEquality(petEntity, cachedPet)
    }

    @Test
    fun mapToCachedMediaMapsData() {
        val medias = PetFactory.makeStringList(3)
        val mediaDbEntities = petEntityMapper.mapToCachedMedia(medias, randomLong())

        assertMediaDataEquality(medias, mediaDbEntities)
    }

    @Test
    fun mapFromCachedMediaMapsData() {
        val mediaDbEntities = PetFactory.makeMediaDbEntityList(3)
        val medias = petEntityMapper.mapFromCachedMedia(mediaDbEntities)

        assertMediaDataEquality(medias, mediaDbEntities)
    }

    @Test
    fun mapToCachedBreedMapsData() {
        val breeds = PetFactory.makeStringList(2)
        val breedDbEntities = petEntityMapper.mapToCachedBreed(breeds, randomLong())

        assertBreedDataEquality(breeds, breedDbEntities)
    }

    @Test
    fun mapFromCachedBreedMapsData() {
        val breedDbEntities = PetFactory.makeBreedDbEntityList(2)
        val breeds = petEntityMapper.mapFromCachedBreed(breedDbEntities)

        assertBreedDataEquality(breeds, breedDbEntities)
    }

    private fun assertPetDataEquality(petEntity: PetEntity, cachedPet: PetDbEntity) {
        assertEquals(petEntity.name, cachedPet.name)
        assertEquals(petEntity.sex, cachedPet.sex)
        assertEquals(petEntity.status, cachedPet.status)
        assertEquals(petEntity.size, cachedPet.size)
        assertEquals(petEntity.shelterId, cachedPet.shelterId)
        assertEquals(petEntity.mix, cachedPet.mix)
        assertEquals(petEntity.lastUpdate, cachedPet.lastUpdate)
        assertEquals(petEntity.id, cachedPet.id)
        assertEquals(petEntity.description, cachedPet.description)
        assertEquals(petEntity.cityState, cachedPet.cityState)
        assertEquals(petEntity.animal, cachedPet.animal)
        assertEquals(petEntity.age, cachedPet.age)
        assertEquals(petEntity.breeds, cachedPet.breeds)
        assertEquals(petEntity.medias, cachedPet.medias)
    }

    private fun assertMediaDataEquality(medias: List<String>, cachedMedias: List<MediaDbEntity>) {
        assertEquals(medias.size, cachedMedias.size)
        medias.forEachIndexed { index, media ->
            assertEquals(media, cachedMedias[index].value)
        }
    }

    private fun assertBreedDataEquality(medias: List<String>, cachedBreeds: List<BreedDbEntity>) {
        assertEquals(medias.size, cachedBreeds.size)
        medias.forEachIndexed { index, media ->
            assertEquals(media, cachedBreeds[index].value)
        }
    }
}