package com.zackyzhang.shelteradoptable.domain.test.factory

import com.zackyzhang.petadoptable.data.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.domain.model.Shelter

class ShelterFactory {

    companion object Factory {

        fun makeShelter(): Shelter {
            return Shelter(randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid(), randomUuid())
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