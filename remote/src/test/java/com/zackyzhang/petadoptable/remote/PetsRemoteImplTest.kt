package com.zackyzhang.petadoptable.remote

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.remote.mapper.PetEntityMapper
import com.zackyzhang.petadoptable.remote.model.GetPetsResponse
import com.zackyzhang.petadoptable.remote.test.factory.PetsFactory
import io.reactivex.Flowable
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

    private fun stubPetsServiceGetPets(observer: Flowable<GetPetsResponse>) {
        whenever(petFinderService.getPets(mutableMapOf()))
                .thenReturn(observer)
    }
}