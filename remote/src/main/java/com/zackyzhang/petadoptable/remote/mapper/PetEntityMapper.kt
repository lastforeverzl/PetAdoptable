package com.zackyzhang.petadoptable.remote.mapper

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.remote.model.Pet
import javax.inject.Inject

/**
 * Map a [Pet] to and from a [PetEntity] instance when data is moving between
 * this layer and the Data layer
 */
open class PetEntityMapper @Inject constructor() : EntityMapper<Pet, PetEntity> {

    companion object {
        const val EMPTY_STRING = ""
    }

    override fun mapFromRemote(type: Pet): PetEntity{
        val status = type.status?.let { it.value } ?:run { EMPTY_STRING }
        val cityState = "%s, %s".format(type.contact.city!!.value, type.contact.state!!.value)
        val age = type.age?.let { it.value } ?:run { EMPTY_STRING }
        val size = type.size?.let { it.value } ?:run { EMPTY_STRING }
        val id = type.id?.let { it.value } ?:run { EMPTY_STRING }
        val media = mutableListOf<String>()
        type.media.photos?.let {
            it.photoList.forEach { item ->
                when (item.size) {
                    "x" -> media.add(item.value)
                }
            }
        }
        val breeds = mutableListOf<String>()
        type.breeds.breed?.let { it.forEach { item -> breeds.add(item.value) } }
        val name = type.name?.let { it.value } ?:run { EMPTY_STRING }
        val sex = type.sex?.let { it.value } ?:run { EMPTY_STRING }
        val description = type.description?.let { it.value } ?:run { EMPTY_STRING }
        val mix = type.mix?.let { it.value } ?:run { EMPTY_STRING }
        val shelterId = type.shelterId?.let { it.value } ?:run { EMPTY_STRING }
        val lastUpdate = type.lastUpdate?.let { it.value } ?:run { EMPTY_STRING }
        val animal = type.animal?.let { it.value } ?:run { EMPTY_STRING }

        return PetEntity(status, cityState, age, size, media,
                id, breeds, name, sex, description,
                mix, shelterId, lastUpdate, animal)
    }

}