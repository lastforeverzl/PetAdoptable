package com.zackyzhang.petadoptable.remote.mapper

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.remote.model.Pet
import javax.inject.Inject

/**
 * Map a [Pet] to and from a [PetEntity] instance when data is moving between
 * this layer and the Data layer
 */
open class PetEntityMapper @Inject constructor() : EntityMapper<Pet, PetEntity> {

    override fun mapFromRemote(type: Pet): PetEntity{
        val cityState = "%s, %s".format(type.contact.city.value, type.contact.state.value)
        val media = mutableListOf<String>()
        type.media.photos.photoList.forEach { item ->
            when(item.size) {
                "x" -> media.add(item.value)
            }
        }
        val breeds = mutableListOf<String>()
        type.breeds.breed.forEach { item -> breeds.add(item.value) }
        return PetEntity(type.status.value, cityState, type.age.value, type.size.value, media,
                type.id.value, breeds, type.name.value, type.sex.value, type.description.value,
                type.mix.value, type.shelterId.value, type.lastUpdate.value, type.animal.value)
    }

}