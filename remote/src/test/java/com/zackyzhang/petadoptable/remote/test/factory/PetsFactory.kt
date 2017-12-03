package com.zackyzhang.petadoptable.remote.test.factory

import com.zackyzhang.petadoptable.remote.model.*
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by lei on 12/2/17.
 */
class PetsFactory {

    companion object Factory {

        fun makePetsResposne(): GetPetsResponse {
            val petsResposne = GetPetsResponse(makeGetPets())
            return petsResposne
        }

        fun makeGetPets(): GetPets {
            return GetPets(makeLastOffset(), makePets(), makeHeader())
        }

        fun makeLastOffset(): LastOffset {
            return LastOffset("25")
        }

        fun makeHeader(): Header {
            return Header(Status(Code("100")))
        }

        fun makePets(): Pets {
            return Pets(makePetList(5))
        }

        fun makePetList(count: Int): List<Pet> {
            val petList = mutableListOf<Pet>()
            repeat(count) {
                petList.add(makePetModel())
            }
            return petList
        }

        fun makePetModel(): Pet {
            val status = PetStatus(randomUuid())
            val contact = Contact(State(randomUuid()), City(randomUuid()))
            val age = Age(randomUuid())
            val size = Size(randomUuid())

            val photo = Photo("x", randomUuid(), randomUuid())
            val photos = Photos(mutableListOf(photo))
            val media = Media(photos)

            val id = Id(randomIntInString())

            val breed = Breed(randomUuid())
            val breeds = Breeds(mutableListOf(breed))

            val name = Name(randomUuid())
            val sex = Sex(randomUuid())
            val description = Description(randomUuid())
            val mix = Mix(randomUuid())
            val shelterId = ShelterId(randomUuid())
            val lastUpdate = LastUpdate(randomIntInString())
            val animal = Animal(randomUuid())
            return Pet(status, contact, age, size, media, id, breeds, name,
                    sex, description, mix, shelterId, lastUpdate, animal)
        }

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomIntInString(): String {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1).toString()
        }

    }

}