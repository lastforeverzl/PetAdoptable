package com.zackyzhang.petadoptable.domain.model

data class PetDetail(val petStatus: String, val petCityState: String, val petAge: String, val petSize: String,
                     val petMedias: List<String>, val petId: String, val petBreeds: List<String>,
                     val petName: String, val petSex: String, val petDescription: String, val petMix: String,
                     val petShelterId: String, val petLastUpdate: String, val petAnimal: String,
                     val petIsFavorite: Boolean = false,
                     val shelterCountry: String, val shelterLongitude: String, val shelterName: String,
                     val shelterPhone: String, val shelterState: String, val shelterAddress2: String,
                     val shelterEmail: String, val shelterCity: String, val shelterZip: String,
                     val shelterFax: String, val shelterLatitude: String, val shelterId: String,
                     val shelterAddress1: String)

