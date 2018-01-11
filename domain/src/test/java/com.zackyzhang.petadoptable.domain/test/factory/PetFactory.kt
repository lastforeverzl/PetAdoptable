package com.zackyzhang.petadoptable.data.test.factory

import com.zackyzhang.petadoptable.data.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.domain.model.Pet

class PetFactory {

    companion object Factory {

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

        fun makePetList(count: Int): List<Pet> {
            val pets = mutableListOf<Pet>()
            repeat(count) {
                pets.add(makePet())
            }
            return pets
        }
    }
}