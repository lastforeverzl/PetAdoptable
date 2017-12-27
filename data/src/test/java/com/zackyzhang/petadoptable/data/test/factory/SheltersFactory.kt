package com.zackyzhang.shelteradoptable.data.test.factory

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.domain.model.Shelter

/**
 * Created by lei on 12/22/17.
 */
class SheltersFactory {

    companion object Factory {

        fun makeShelterEntity(): ShelterEntity {
            return ShelterEntity(randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeShelter(): Shelter {

            return Shelter(randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeShelterEntityList(count: Int): List<ShelterEntity> {
            val shelterEntities = mutableListOf<ShelterEntity>()
            repeat(count) {
                shelterEntities.add(makeShelterEntity())
            }
            return shelterEntities
        }

        fun makeShelterList(count: Int): List<Shelter> {
            val shelters = mutableListOf<Shelter>()
            repeat(count) {
                shelters.add(makeShelter())
            }
            return shelters
        }
    }
}