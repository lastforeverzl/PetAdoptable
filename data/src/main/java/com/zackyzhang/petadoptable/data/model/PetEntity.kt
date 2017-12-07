package com.zackyzhang.petadoptable.data.model

/**
 * Representation for a [PetEntity] fetched from an external layer data source
 */
data class PetEntity(val status: String, val cityState: String, val age: String, val size: String,
                     var medias: List<String>, val id: String, var breeds: List<String>,
                     val name: String, val sex: String, val description: String, val mix: String,
                     val shelterId: String, val lastUpdate: String, val animal: String)
