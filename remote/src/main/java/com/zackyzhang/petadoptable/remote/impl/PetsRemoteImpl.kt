package com.zackyzhang.petadoptable.remote.impl

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsRemote
import com.zackyzhang.petadoptable.remote.PetFinderService
import com.zackyzhang.petadoptable.remote.mapper.PetEntityMapper
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class PetsRemoteImpl @Inject constructor(private val petFinderService: PetFinderService,
                                         private val petEntityMapper: PetEntityMapper) :
        PetsRemote {

    override fun getPets(options: Map<String, String>): Flowable<List<PetEntity>> {
        return petFinderService.getPets(options)
                .map { result ->
                    result.petfinder.pets.petList.map { listItem ->
                        petEntityMapper.mapFromRemote(listItem)
                    }
                }
    }

    override fun getPetById(options: Map<String, String>): Single<PetEntity> {
        return petFinderService.getPetById(options)
                .map { result ->
                    petEntityMapper.mapFromRemote(result.petfinder.pet)
                }
    }

    override fun getShelterPets(options: Map<String, String>): Flowable<List<PetEntity>> {
        return petFinderService.getShelterPets(options)
                .map { result ->
                    result.petfinder.pets.petList.map { listItem ->
                        petEntityMapper.mapFromRemote(listItem)
                    }
                }
    }

}