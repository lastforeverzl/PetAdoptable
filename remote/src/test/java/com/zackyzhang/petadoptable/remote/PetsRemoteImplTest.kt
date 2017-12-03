package com.zackyzhang.petadoptable.remote

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.remote.mapper.PetsEntityMapper
import com.zackyzhang.petadoptable.remote.model.GetPetsResponse
import com.zackyzhang.petadoptable.remote.test.factory.PetsFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by lei on 12/1/17.
 */
@RunWith(JUnit4::class)
class PetsRemoteImplTest {

    private lateinit var key: String
    private lateinit var location: String
    private lateinit var entityMapper: PetsEntityMapper
    private lateinit var petFinderService: PetFinderService

    private lateinit var petsRemoteImpl: PetsRemoteImpl

    @Before
    fun setup() {
        key = "53e8b3b7be61b102e2fd238aedf46dd1"
        location = "94568"
        entityMapper = mock()
        petFinderService = mock()
        petsRemoteImpl = PetsRemoteImpl(petFinderService, entityMapper)
    }

    @Test
    fun getPetsComplete() {
        stubPetsServiceGetPets(Single.just(PetsFactory.makePetsResposne()))
        val testObserver = petsRemoteImpl.getPets(key, location, mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPetsReturnData() {
        val getPetsResposne = PetsFactory.makePetsResposne()
        stubPetsServiceGetPets(Single.just(getPetsResposne))
        val petEntities = mutableListOf<PetEntity>()
        getPetsResposne.petfinder.pets.petList.forEach {
            petEntities.add(entityMapper.mapFromRemote(it))
        }
        val testObserver = petsRemoteImpl.getPets(key, location, mutableMapOf()).test()
        testObserver.assertValue(petEntities)
    }

    private fun stubPetsServiceGetPets(single: Single<GetPetsResponse>) {
        whenever(petFinderService.getPets(key, location, mutableMapOf()))
                .thenReturn(single)
    }
}