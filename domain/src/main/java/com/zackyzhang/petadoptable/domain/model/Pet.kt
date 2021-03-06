package com.zackyzhang.petadoptable.domain.model

/**
 * Representation for a [Pet] fetched from an external layer data source
 */
data class Pet(val status: String, val cityState: String, val age: String, val size: String,
               val medias: List<String>, val id: String, val breeds: List<String>, val name: String,
               val sex: String, val description: String, val mix: String, val shelterId: String,
               val lastUpdate: String, val animal: String, val isFavorite: Boolean = false)