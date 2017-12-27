package com.zackyzhang.petadoptable.remote.mapper

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.remote.model.Shelter
import javax.inject.Inject

/**
 * Map a [Shelter] to and from a [ShelterEntity] instance when data is moving between
 * this layer and the Data layer
 */
open class ShelterEntityMapper @Inject constructor(): EntityMapper<Shelter, ShelterEntity> {

    companion object {
        const val EMPTY_STRING = ""
    }

    override fun mapFromRemote(type: Shelter): ShelterEntity {
        val country = type.country?.let { it.value } ?:run { EMPTY_STRING }
        val longitude = type.longitude?.let { it.value } ?:run { EMPTY_STRING }
        val name = type.name?.let { it.value } ?:run { EMPTY_STRING }
        val phone = type.phone?.let { it.value } ?:run { EMPTY_STRING }
        val state = type.state?.let { it.value } ?:run { EMPTY_STRING }
        val address2 = type.address2?.let { it.value } ?:run { EMPTY_STRING }
        val email = type.email?.let { it.value } ?:run { EMPTY_STRING }
        val city = type.city?.let { it.value } ?:run { EMPTY_STRING }
        val zip = type.zip?.let { it.value } ?:run { EMPTY_STRING }
        val fax = type.fax?.let { it.value } ?:run { EMPTY_STRING }
        val latitude = type.latitude?.let { it.value } ?:run { EMPTY_STRING }
        val id = type.id?.let { it.value } ?:run { EMPTY_STRING }
        val address1 = type.address1?.let { it.value } ?:run { EMPTY_STRING }
        return ShelterEntity(country, longitude, name, phone, state, address2, email, city, zip, fax,
                latitude, id, address1)
    }

}
