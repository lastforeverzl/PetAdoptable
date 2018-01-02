package com.zackyzhang.petadoptable.presentation.model

/**
 * Representation for a [PetDetailView] instance for this layers Model representation
 */
class PetDetailView(val petStatus: String, val petCityState: String, val petAge: String, val petSize: String,
                    val petMedias: List<String>, val petId: String, val petBreeds: List<String>,
                    val petName: String, val petSex: String, val petDescription: String, val petMix: String,
                    val petShelterId: String, val petLastUpdate: String, val petAnimal: String,
                    val petIsFavorite: Boolean = false, val shelterName: String, val shelterPhone: String,
                    val shelterEmail: String, val shelterLongitude: String, val shelterLatitude: String,
                    val shelterId: String, val shelterAddress: String)