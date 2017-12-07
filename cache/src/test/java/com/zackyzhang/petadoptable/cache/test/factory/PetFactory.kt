package com.zackyzhang.petadoptable.cache.test.factory

import com.zackyzhang.petadoptable.cache.db.entity.BreedDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.MediaDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.PetDbEntity
import com.zackyzhang.petadoptable.cache.test.factory.DataFactory.Factory.randomLong
import com.zackyzhang.petadoptable.cache.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.data.model.PetEntity

/**
 * Created by lei on 12/6/17.
 */
class PetFactory {

    companion object Factory {

        fun makeCachedPet(): PetDbEntity {
            val medias = mutableListOf<String>()
            medias.add(DataFactory.randomUuid())
            medias.add(DataFactory.randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(DataFactory.randomUuid())
            breeds.add(DataFactory.randomUuid())

            return PetDbEntity(null, DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    medias, DataFactory.randomUuid(), breeds, DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makePetEntity(): PetEntity {
            val medias = mutableListOf<String>()
            medias.add(DataFactory.randomUuid())
            medias.add(DataFactory.randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(DataFactory.randomUuid())
            breeds.add(DataFactory.randomUuid())

            return PetEntity(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    medias, DataFactory.randomUuid(), breeds, DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makePetEntityList(count: Int): List<PetEntity> {
            val petEntities = mutableListOf<PetEntity>()
            repeat(count) {
                petEntities.add(makePetEntity())
            }
            return petEntities
        }

        fun makeCachedPetList(count: Int): List<PetDbEntity> {
            val cachedPets = mutableListOf<PetDbEntity>()
            repeat(count) {
                cachedPets.add(makeCachedPet())
            }
            return cachedPets
        }

        fun makeMediaDbEntityList(count: Int): List<MediaDbEntity> {
            val mediaEntities = mutableListOf<MediaDbEntity>()
            repeat(count) {
                mediaEntities.add(makeMediaDbEntity())
            }
            return mediaEntities
        }

        fun makeBreedDbEntityList(count: Int): List<BreedDbEntity> {
            val mediaEntities = mutableListOf<BreedDbEntity>()
            repeat(count) {
                mediaEntities.add(makeBreedDbEntity())
            }
            return mediaEntities
        }

        fun makeStringList(count: Int): List<String> {
            val list = mutableListOf<String>()
            repeat(count) {
                list.add(randomUuid())
            }
            return list
        }

        fun makeMediaDbEntity(): MediaDbEntity {
            return MediaDbEntity(null, randomLong(), randomUuid())
        }

        fun makeBreedDbEntity(): BreedDbEntity {
            return BreedDbEntity(null, randomLong(), randomUuid())
        }

    }
}