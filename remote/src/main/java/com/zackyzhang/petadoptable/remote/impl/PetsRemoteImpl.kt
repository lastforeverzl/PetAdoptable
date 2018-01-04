package com.zackyzhang.petadoptable.remote.impl

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsRemote
import com.zackyzhang.petadoptable.remote.PetFinderService
import com.zackyzhang.petadoptable.remote.mapper.PetEntityMapper
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by lei on 12/1/17.
 */
class PetsRemoteImpl @Inject constructor(private val petFinderService: PetFinderService,
                                         private val entityMapper: PetEntityMapper) :
        PetsRemote {

    override fun getPets(options: Map<String, String>): Flowable<List<PetEntity>> {
        return petFinderService.getPets(options)
                .map { result ->
                    result.petfinder.pets.petList.map { listItem ->
                        entityMapper.mapFromRemote(listItem)
                    }
                }
    }

    override fun getPetById(options: Map<String, String>): Single<PetEntity> {
        return petFinderService.getPetById(options)
                .map { result ->
                    entityMapper.mapFromRemote(result.petfinder.pet)
                }
    }

}