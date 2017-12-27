package com.zackyzhang.shelteradoptable.remote.test.factory

import com.zackyzhang.petadoptable.remote.model.*
import com.zackyzhang.petadoptable.remote.test.factory.DataFactory.factory.randomUuid

/**
 * Created by lei on 12/22/17.
 */
class SheltersFactory {

    companion object {
        fun makeSheltersResposne(): GetSheltersResponse {
            val sheltersResposne = GetSheltersResponse(makeGetShelters())
            return sheltersResposne
        }

        fun makeGetShelters(): GetShelters {
            return GetShelters(makeLastOffset(), makeShelters(), makeHeader())
        }

        fun makeLastOffset(): LastOffset {
            return LastOffset("25")
        }

        fun makeHeader(): Header {
            return Header(Status(Code("100")))
        }

        fun makeShelters(): Shelters {
            return Shelters(makeShelterList(5))
        }

        fun makeShelterList(count: Int): List<Shelter> {
            val shelterList = mutableListOf<Shelter>()
            repeat(count) {
                shelterList.add(makeShelterModel())
            }
            return shelterList
        }

        fun makeShelterModel(): Shelter {
            val country = Country(randomUuid())
            val longitude = Longitude(randomUuid())
            val name = Name(randomUuid())
            val phone = Phone(randomUuid())
            val state = State(randomUuid())
            val address2 = Address2(randomUuid())
            val email = Email(randomUuid())
            val city = City(randomUuid())
            val zip = Zip(randomUuid())
            val fax = Fax(randomUuid())
            val latitude = Latitude(randomUuid())
            val id = Id(randomUuid())
            val address1 = Address1(randomUuid())
            return Shelter(country, longitude, name, phone, state, address2, email, city, zip, fax,
                    latitude, id, address1)
        }
    }

}