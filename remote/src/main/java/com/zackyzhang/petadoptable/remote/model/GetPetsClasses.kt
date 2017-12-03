package com.zackyzhang.petadoptable.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Created by lei on 11/29/17.
 */
data class GetPetsResponse(val petfinder: GetPets)

data class GetPets(val lastOffset: LastOffset, val pets: Pets, val header: Header)

data class LastOffset(val value: String)

data class Pets(@SerializedName("pet") val petList: List<Pet>)