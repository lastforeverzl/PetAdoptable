package com.zackyzhang.petadoptable.remote

import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsRemote
import com.zackyzhang.petadoptable.remote.mapper.PetEntityMapper
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by lei on 12/1/17.
 */
class PetsRemoteImpl @Inject constructor(private val petFinderService: PetFinderService,
                                         private val entityMapper: PetEntityMapper) :
        PetsRemote {

    override fun getPets(key: String, location: String, options: Map<String, String>):
            Flowable<List<PetEntity>> {
        return petFinderService.getPets(key, location, options)
                .map { result ->
                    result.petfinder.pets.petList.map { listItem ->
                        entityMapper.mapFromRemote(listItem)
                    }
                }
    }

}