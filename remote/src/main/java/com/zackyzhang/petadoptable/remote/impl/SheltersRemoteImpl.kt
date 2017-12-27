package com.zackyzhang.petadoptable.remote.impl

import com.zackyzhang.petadoptable.data.model.ShelterEntity
import com.zackyzhang.petadoptable.data.repository.SheltersRemote
import com.zackyzhang.petadoptable.remote.PetFinderService
import com.zackyzhang.petadoptable.remote.mapper.ShelterEntityMapper
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by lei on 12/21/17.
 */
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

}