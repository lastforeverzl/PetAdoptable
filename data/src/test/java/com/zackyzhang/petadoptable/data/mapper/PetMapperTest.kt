package com.zackyzhang.petadoptable.data.mapper

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.test.factory.PetFactory
import com.zackyzhang.petadoptable.domain.model.Pet
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

/**
 * Created by lei on 12/3/17.
 */

@RunWith(JUnit4::class)
class PetMapperTest {
    private lateinit var petMapper: PetMapper

    @Before
    fun setup() {
        petMapper = PetMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val petEntity = PetFactory.makePetEntity()
        val pet = petMapper.mapFromEntity(petEntity)

        assertPetDataEquality(petEntity, pet)
    }

    @Test
    fun mapToEntityMapsData() {
        val pet = PetFactory.makePet()
        val petEntity = petMapper.mapToEntity(pet)

        assertPetDataEquality(petEntity, pet)
    }

    private fun assertPetDataEquality(petEntity: PetEntity, pet: Pet) {
        assertEquals(petEntity.age, pet.age)
        assertEquals(petEntity.animal, pet.animal)
        assertEquals(petEntity.breeds, pet.breeds)
        assertEquals(petEntity.cityState, pet.cityState)
        assertEquals(petEntity.description, pet.description)
        assertEquals(petEntity.id, pet.id)
        assertEquals(petEntity.lastUpdate, pet.lastUpdate)
        assertEquals(petEntity.media, pet.media)
        assertEquals(petEntity.mix, pet.mix)
        assertEquals(petEntity.name, pet.name)
        assertEquals(petEntity.shelterId, pet.shelterId)
        assertEquals(petEntity.size, pet.size)
        assertEquals(petEntity.status, pet.status)
        assertEquals(petEntity.sex, pet.sex)
    }
}