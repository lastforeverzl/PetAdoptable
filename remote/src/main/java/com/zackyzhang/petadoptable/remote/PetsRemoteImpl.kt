package com.zackyzhang.petadoptable.remote

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsRemote
import com.zackyzhang.petadoptable.remote.mapper.PetsEntityMapper
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by lei on 12/1/17.
 */
class PetsRemoteImpl @Inject constructor(private val petFinderService: PetFinderService,
                                         private val entityMapper: PetsEntityMapper) :
        PetsRemote {

    override fun getPets(key: String, location: String, options: Map<String, String>):
            Single<List<PetEntity>> {
        return petFinderService.getPets(key, location, options)
                .map { result ->
                    result.petfinder.pets.petList.map { listItem ->
                        entityMapper.mapFromRemote(listItem)
                    }
                }
    }

}