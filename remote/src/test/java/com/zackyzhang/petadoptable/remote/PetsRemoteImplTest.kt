package com.zackyzhang.petadoptable.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.remote.impl.PetsRemoteImpl
import com.zackyzhang.petadoptable.remote.mapper.PetEntityMapper
import com.zackyzhang.petadoptable.remote.model.GetPetResponse
import com.zackyzhang.petadoptable.remote.model.GetPetsResponse
import com.zackyzhang.petadoptable.remote.test.factory.PetsFactory
import io.reactivex.Flowable
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

    private lateinit var entityMapper: PetEntityMapper
    private lateinit var petFinderService: PetFinderService

    private lateinit var petsRemoteImpl: PetsRemoteImpl

    @Before
    fun setup() {
        entityMapper = mock()
        petFinderService = mock()
        petsRemoteImpl = PetsRemoteImpl(petFinderService, entityMapper)
    }

    @Test
    fun getPetsComplete() {
        stubPetsServiceGetPets(Flowable.just(PetsFactory.makePetsResposne()))
        val testObserver = petsRemoteImpl.getPets(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPetsReturnData() {
        val getPetsResposne = PetsFactory.makePetsResposne()
        stubPetsServiceGetPets(Flowable.just(getPetsResposne))
        val petEntities = mutableListOf<PetEntity>()
        getPetsResposne.petfinder.pets.petList.forEach {
            petEntities.add(entityMapper.mapFromRemote(it))
        }
        val testObserver = petsRemoteImpl.getPets(mutableMapOf()).test()
        testObserver.assertValue(petEntities)
    }

    @Test
    fun getPetByIdComplete() {
        stubPetsServiceGetPetById(Single.just(PetsFactory.makeGetPetResponse()))
        stubPetsServiceMapFromRemote(PetsFactory.makePetEntity())
        val testObserver = petsRemoteImpl.getPetById(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getPetByIdReturnData() {
        val getPetResponse = PetsFactory.makeGetPetResponse()
        stubPetsServiceGetPetById(Single.just(getPetResponse))
        val petEntity = PetsFactory.makePetEntity()
        stubPetsServiceMapFromRemote(petEntity)

        val testObserver = petsRemoteImpl.getPetById(mutableMapOf()).test()
        testObserver.assertValue(petEntity)
    }

    private fun stubPetsServiceGetPets(observer: Flowable<GetPetsResponse>) {
        whenever(petFinderService.getPets(mutableMapOf()))
                .thenReturn(observer)
    }

    private fun stubPetsServiceGetPetById(single: Single<GetPetResponse>) {
        whenever(petFinderService.getPetById(mutableMapOf()))
                .thenReturn(single)
    }

    private fun stubPetsServiceMapFromRemote(petEntity: PetEntity) {
        whenever(entityMapper.mapFromRemote(any()))
                .thenReturn(petEntity)
    }
}