package com.zackyzhang.shelteradoptable.presentation.test.factory

import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.petadoptable.presentation.model.ShelterView
import com.zackyzhang.petadoptable.presentation.test.factory.DataFactory.Factory.randomUuid


/**
 * Created by lei on 12/22/17.
 */
class SheltersFactory {

    companion object {

        fun makeShelterList(count: Int): List<Shelter> {
            val shelters = mutableListOf<Shelter>()
            repeat(count) {
                shelters.add(makeShelter())
            }
            return shelters
        }

        fun makeShelter(): Shelter {
            val medias = mutableListOf<String>()
            medias.add(randomUuid())
            medias.add(randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(randomUuid())

            return Shelter(randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

        fun makeShelterViewList(count: Int): List<ShelterView> {
            val shelters = mutableListOf<ShelterView>()
            repeat(count) {
                shelters.add(makeShelterView())
            }
            return shelters
        }

        fun makeShelterView(): ShelterView {
            val medias = mutableListOf<String>()
            medias.add(randomUuid())
            medias.add(randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(randomUuid())

            return ShelterView(randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid())
        }

    }
}