package com.zackyzhang.petadoptable.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.remote.impl.SheltersRemoteImpl
import com.zackyzhang.petadoptable.remote.mapper.ShelterEntityMapper
import com.zackyzhang.petadoptable.remote.model.GetShelterResponse
import com.zackyzhang.petadoptable.remote.model.GetSheltersResponse
import com.zackyzhang.shelteradoptable.remote.test.factory.SheltersFactory
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by lei on 12/22/17.
 */
@RunWith(JUnit4::class)
class SheltersRemoteImplTest {

    private lateinit var entityMapper: ShelterEntityMapper
    private lateinit var petFinderService: PetFinderService

    private lateinit var petsRemoteImpl: SheltersRemoteImpl

    @Before
    fun setup() {
        entityMapper = mock()
        petFinderService = mock()
        petsRemoteImpl = SheltersRemoteImpl(petFinderService, entityMapper)
    }

    @Test
    fun getSheltersComplete() {
        stubSheltersServiceGetShelters(Flowable.just(SheltersFactory.makeSheltersResposne()))
        val testObserver = petsRemoteImpl.getShelters(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getSheltersReturnData() {
        val getSheltersResposne = SheltersFactory.makeSheltersResposne()
        stubSheltersServiceGetShelters(Flowable.just(getSheltersResposne))
        val petEntities = mutableListOf<ShelterEntity>()
        getSheltersResposne.petfinder.shelters.shelterList.forEach {
            petEntities.add(entityMapper.mapFromRemote(it))
        }
        val testObserver = petsRemoteImpl.getShelters(mutableMapOf()).test()
        testObserver.assertValue(petEntities)
    }

    @Test
    fun getShelterByIdCompletes() {
        val shelterEntity = SheltersFactory.makeShelterEntity()
        stubSheltersServiceMapFromRemote(shelterEntity)
        stubSheltersServiceGetShelterById(Single.just(SheltersFactory.makeShelterResponse()))
        val testObserver = petsRemoteImpl.getShelterById(mutableMapOf()).test()
        testObserver.assertComplete()
    }

    @Test
    fun getShelterByIdReturnData() {
        val getShelterResponse = SheltersFactory.makeShelterResponse()
        stubSheltersServiceGetShelterById(Single.just(getShelterResponse))
        val shelterEntity = SheltersFactory.makeShelterEntity()
        stubSheltersServiceMapFromRemote(shelterEntity)

        val testObserver = petsRemoteImpl.getShelterById(mutableMapOf()).test()
        testObserver.assertValue(shelterEntity)
    }

    private fun stubSheltersServiceGetShelters(observer: Flowable<GetSheltersResponse>) {
        whenever(petFinderService.getShelters(mutableMapOf()))
                .thenReturn(observer)
    }

    private fun stubSheltersServiceGetShelterById(observer: Single<GetShelterResponse>) {
        whenever(petFinderService.getShelterById(mutableMapOf()))
                .thenReturn(observer)
    }

    private fun stubSheltersServiceMapFromRemote(shelterEntity: ShelterEntity) {
        whenever(entityMapper.mapFromRemote(any()))
                .thenReturn(shelterEntity)
    }
}