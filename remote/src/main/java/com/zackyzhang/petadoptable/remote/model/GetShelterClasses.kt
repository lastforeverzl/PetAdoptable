package com.zackyzhang.petadoptable.remote.model

/**
 * Created by lei on 11/30/17.
 */
data class GetShelterResponse(val petfinder: GetShelter)

data class GetShelter(val shelter: Shelter, val header: Header)