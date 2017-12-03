package com.zackyzhang.petadoptable.remote.mapper

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.remote.model.Pet
import javax.inject.Inject

/**
 * Created by lei on 12/1/17.
 */
open class PetsEntityMapper @Inject constructor() : EntityMapper<Pet, PetEntity> {

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