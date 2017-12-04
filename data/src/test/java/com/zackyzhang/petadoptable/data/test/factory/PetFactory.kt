package com.zackyzhang.petadoptable.data.test.factory

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.domain.model.Pet

/**
 * Created by lei on 12/3/17.
 */
class PetFactory {

    companion object factory {

        fun makePetEntity(): PetEntity {
            val medias = mutableListOf<String>()
            medias.add(randomUuid())
            medias.add(randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(randomUuid())

            return PetEntity(randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    medias, randomUuid(), breeds, randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid())
        }

        fun makePet(): Pet {
            val medias = mutableListOf<String>()
            medias.add(randomUuid())
            medias.add(randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(randomUuid())

            return Pet(randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    medias, randomUuid(), breeds, randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid())
        }

        fun makePetEntityList(count: Int): List<PetEntity> {
            val petEntities = mutableListOf<PetEntity>()
            repeat(count) {
                petEntities.add(makePetEntity())
            }
            return petEntities
        }

        fun makePetList(count: Int): List<Pet> {
            val pets = mutableListOf<Pet>()
            repeat(count) {
                pets.add(makePet())
            }
            return pets
        }
    }
}