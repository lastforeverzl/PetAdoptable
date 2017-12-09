package com.zackyzhang.petadoptable.presentation.test.factory

import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.presentation.model.PetView
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory.Factory.randomUuid


/**
 * Created by lei on 12/8/17.
 */
class PetsFactory {

    companion object Factory {

        fun makePetList(count: Int): List<Pet> {
            val pets = mutableListOf<Pet>()
            repeat(count) {
                pets.add(makePet())
            }
            return pets
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

        fun makePetViewList(count: Int): List<PetView> {
            val pets = mutableListOf<PetView>()
            repeat(count) {
                pets.add(makePetView())
            }
            return pets
        }

        fun makePetView(): PetView {
            val medias = mutableListOf<String>()
            medias.add(randomUuid())
            medias.add(randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(randomUuid())

            return PetView(randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    medias, randomUuid(), breeds, randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid())
        }

    }
}