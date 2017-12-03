package com.zackyzhang.petadoptable.data.model

/**
 * Created by lei on 12/1/17.
 */
data class PetEntity(val status: String, val cityState: String, val age: String, val size: String,
                     val media: List<String>, val id: String, val breeds: List<String>,
                     val name: String, val sex: String, val description: String, val mix: String,
                     val shelterId: String, val lastUpdate: String, val animal: String)
