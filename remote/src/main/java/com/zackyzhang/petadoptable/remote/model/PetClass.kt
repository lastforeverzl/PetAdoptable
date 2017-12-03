package com.zackyzhang.petadoptable.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by lei on 11/29/17.
 */
data class Pet(val status: PetStatus,
               val contact: Contact,
               val age: Age,
               val size: Size,
               val media: Media,
               val id: Id,
//               @SerializedName("shelterPetId") val shelterPetId: ShelterPetId,
               val breeds: Breeds,
               val name: Name,
               val sex: Sex,
               val description: Description,
               val mix: Mix,
               @SerializedName("shelterId") val shelterId: ShelterId,
               @SerializedName("lastUpdate") val lastUpdate: LastUpdate,
               val animal: Animal)

data class PetStatus(val value: String)

data class Contact(val state: State, val city: City)

data class State(val value: String)

data class City(val value: String)

data class Age( val value: String)

data class Size(val value: String)

data class Media(val photos: Photos)

data class Photos(@SerializedName("photo") val photoList: List<Photo>)

data class Photo(val size: String, val value: String, val id: String)

data class Id(val value: String)

//data class ShelterPetId(@SerializedName("photos") val value: String)

data class Breeds(val breed: List<Breed>)

data class Breed(val value: String)

data class Name(val value: String)

data class Sex(val value: String)

data class Description(val value: String)

data class Mix(val value: String)

data class ShelterId(val value: String)

data class LastUpdate(val value: String)

data class Animal(val value: String)
