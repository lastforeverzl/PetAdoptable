package com.zackyzhang.petadoptable.remote.model

data class GetPetResponse(val petfinder: GetPet)

data class GetPet(val pet: Pet)