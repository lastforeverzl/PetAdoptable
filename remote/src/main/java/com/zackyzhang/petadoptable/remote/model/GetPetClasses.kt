package com.zackyzhang.petadoptable.remote.model

/**
 * Created by lei on 11/30/17.
 */
data class GetPetResponse(val petfinder: GetPet)

data class GetPet(val pet: Pet)