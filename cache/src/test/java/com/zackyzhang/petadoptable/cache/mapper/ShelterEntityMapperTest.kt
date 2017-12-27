package com.zackyzhang.shelteradoptable.cache.mapper

import com.zackyzhang.petadoptable.cache.db.entity.ShelterDbEntity
import com.zackyzhang.petadoptable.cache.mapper.ShelterEntityMapper
import com.zackyzhang.petadoptable.cache.test.factory.ShelterFactory
import com.zackyzhang.petadoptable.data.model.ShelterEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

/**
 * Created by lei on 12/22/17.
 */
@RunWith(JUnit4::class)
class ShelterEntityMapperTest {

    private lateinit var shelterEntityMapper: ShelterEntityMapper

    @Before
    fun setUp() {
        shelterEntityMapper = ShelterEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val shelterEntity = ShelterFactory.makeShelterEntity()
        val cachedShelter = shelterEntityMapper.mapToCached(shelterEntity)

        assertShelterDataEquality(shelterEntity, cachedShelter)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedShelter = ShelterFactory.makeCachedShelter()
        val shelterEntity = shelterEntityMapper.mapFromCached(cachedShelter)

        assertShelterDataEquality(shelterEntity, cachedShelter)
    }

    private fun assertShelterDataEquality(shelterEntity: ShelterEntity,
                                          cachedShelter: ShelterDbEntity) {

        assertEquals(shelterEntity.address1, cachedShelter.address1)
        assertEquals(shelterEntity.address2, cachedShelter.address2)
        assertEquals(shelterEntity.city, cachedShelter.city)
        assertEquals(shelterEntity.country, cachedShelter.country)
        assertEquals(shelterEntity.email, cachedShelter.email)
        assertEquals(shelterEntity.fax, cachedShelter.fax)
        assertEquals(shelterEntity.id, cachedShelter.id)
        assertEquals(shelterEntity.latitude, cachedShelter.latitude)
        assertEquals(shelterEntity.longitude, cachedShelter.longitude)
        assertEquals(shelterEntity.name, cachedShelter.name)
        assertEquals(shelterEntity.phone, cachedShelter.phone)
        assertEquals(shelterEntity.state, cachedShelter.state)
        assertEquals(shelterEntity.zip, cachedShelter.zip)
    }

}