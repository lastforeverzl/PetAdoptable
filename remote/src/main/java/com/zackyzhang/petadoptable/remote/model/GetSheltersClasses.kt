package com.zackyzhang.petadoptable.remote.model

import com.google.gson.annotations.SerializedName

data class GetSheltersResponse(val petfinder: GetShelters)

data class GetShelters(val lastOffset: LastOffset, val shelters: Shelters, val header: Header)

data class Shelters(@SerializedName("shelter") val shelterList: List<Shelter>)

