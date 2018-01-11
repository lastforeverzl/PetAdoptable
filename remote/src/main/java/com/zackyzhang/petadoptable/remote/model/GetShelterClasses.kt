package com.zackyzhang.petadoptable.remote.model

data class GetShelterResponse(val petfinder: GetShelter)

data class GetShelter(val shelter: Shelter, val header: Header)