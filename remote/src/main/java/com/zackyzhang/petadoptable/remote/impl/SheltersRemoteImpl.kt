package com.zackyzhang.petadoptable.remote.impl

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.repository.SheltersRemote
import com.zackyzhang.petadoptable.remote.PetFinderService
import com.zackyzhang.petadoptable.remote.mapper.ShelterEntityMapper
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class SheltersRemoteImpl @Inject constructor(private val petFinderService: PetFinderService,
                                             private val entityMapper: ShelterEntityMapper) :
        SheltersRemote {

    override fun getShelters(options: Map<String, String>): Flowable<List<ShelterEntity>> {
        return petFinderService.getShelters(options)
                .map { result ->
                    result.petfinder.shelters.shelterList.map { listItem ->
                        entityMapper.mapFromRemote(listItem)
                    }
                }
    }

    override fun getShelterById(options: Map<String, String>): Single<ShelterEntity> {
        return petFinderService.getShelterById(options)
                .map { result ->
                    println(result)
                    println(result.petfinder.shelter)
                    val re = entityMapper.mapFromRemote(result.petfinder.shelter)
                    println(re)
                    re
                }
    }

}