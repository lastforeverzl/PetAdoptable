package com.zackyzhang.shelteradoptable.data.mapper

import com.zackyzhang.petadoptable.data.mapper.ShelterMapper
import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.domain.model.Shelter
import com.zackyzhang.shelteradoptable.data.test.factory.SheltersFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ShelterMapperTest {
    private lateinit var shelterMapper: ShelterMapper

    @Before
    fun setup() {
        shelterMapper = ShelterMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val shelterEntity = SheltersFactory.makeShelterEntity()
        val shelter = shelterMapper.mapFromEntity(shelterEntity)

        assertShelterDataEquality(shelterEntity, shelter)
    }

    @Test
    fun mapToEntityMapsData() {
        val shelter = SheltersFactory.makeShelter()
        val shelterEntity = shelterMapper.mapToEntity(shelter)

        assertShelterDataEquality(shelterEntity, shelter)
    }

    private fun assertShelterDataEquality(shelterEntity: ShelterEntity, shelter: Shelter) {
        assertEquals(shelterEntity.zip, shelter.zip)
        assertEquals(shelterEntity.phone, shelter.phone)
        assertEquals(shelterEntity.longitude, shelter.longitude)
        assertEquals(shelterEntity.latitude, shelter.latitude)
        assertEquals(shelterEntity.fax, shelter.fax)
        assertEquals(shelterEntity.id, shelter.id)
        assertEquals(shelterEntity.email, shelter.email)
        assertEquals(shelterEntity.country, shelter.country)
        assertEquals(shelterEntity.address2, shelter.address2)
        assertEquals(shelterEntity.name, shelter.name)
        assertEquals(shelterEntity.address1, shelter.address1)
        assertEquals(shelterEntity.state, shelter.state)
        assertEquals(shelterEntity.city, shelter.city)
    }
}