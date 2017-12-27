package com.zackyzhang.petadoptable.cache.test.factory

import com.zackyzhang.petadoptable.cache.db.entity.ShelterDbEntity
import com.zackyzhang.petadoptable.cache.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.data.model.ShelterEntity

/**
 * Created by lei on 12/22/17.
 */
class ShelterFactory {

    companion object {

        fun makeCachedShelter(): ShelterDbEntity {
            return ShelterDbEntity(null, randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid(),randomUuid(),randomUuid(),
                    randomUuid(), randomUuid(), randomUuid())
        }

        fun makeShelterEntity(): ShelterEntity {
            return ShelterEntity(randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(),randomUuid(),randomUuid(), randomUuid(),
                    randomUuid(), randomUuid())
        }

        fun makeShelterEntityList(count: Int): List<ShelterEntity> {
            val shelterEntities = mutableListOf<ShelterEntity>()
            repeat(count) {
                shelterEntities.add(makeShelterEntity())
            }
            return shelterEntities
        }

        fun makeCachedShelterList(count: Int): List<ShelterDbEntity> {
            val cachedShelters = mutableListOf<ShelterDbEntity>()
            repeat(count) {
                cachedShelters.add(makeCachedShelter())
            }
            return cachedShelters
        }

        fun makeStringList(count: Int): List<String> {
            val list = mutableListOf<String>()
            repeat(count) {
                list.add(DataFactory.randomUuid())
            }
            return list
        }

    }
}