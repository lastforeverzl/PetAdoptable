package com.zackyzhang.petadoptable.remote.mapper

import com.zackyzhang.petadoptable.remote.test.factory.PetsFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

/**
 * Created by lei on 12/3/17.
 */
@RunWith(JUnit4::class)
class PetsEntityMapperTest {
    private lateinit var petEntityMapper: PetsEntityMapper

    @Before
    fun setup() {
        petEntityMapper = PetsEntityMapper()
    }

    @Test
    fun mapFromRemoteMapsData() {
        val petModel = PetsFactory.makePetModel()
        val petEntity = petEntityMapper.mapFromRemote(petModel)

        assertEquals(petModel.status.value, petEntity.status)
        assertEquals(petModel.name.value, petEntity.name)
        assertEquals(petModel.age.value, petEntity.age)
        assertEquals(petModel.animal.value, petEntity.animal)
        assertEquals(petModel.breeds.breed[0].value, petEntity.breeds[0])
        assertEquals("%s, %s".format(petModel.contact.city.value, petModel.contact.state.value), petEntity.cityState)
        assertEquals(petModel.description.value, petEntity.description)
        assertEquals(petModel.id.value, petEntity.id)
        assertEquals(petModel.lastUpdate.value, petEntity.lastUpdate)
        assertEquals(petModel.media.photos.photoList[0].value, petEntity.media[0])
        assertEquals(petModel.mix.value, petEntity.mix)
        assertEquals(petModel.sex.value, petEntity.sex)
        assertEquals(petModel.size.value, petEntity.size)
        assertEquals(petModel.shelterId.value, petEntity.shelterId)
    }
}