package com.zackyzhang.petadoptable.data

import com.zackyzhang.petadoptable.data.mapper.PetMapper
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.source.PetsDataStoreFactory
import com.zackyzhang.petadoptable.data.source.PetsRemoteDataStore
import com.zackyzhang.petadoptable.domain.model.Pet
import com.zackyzhang.petadoptable.domain.repository.PetsRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Provides an implementation of the [PetsRepository] interface for communicating to and from
 * data sources
 */
class PetsDataRepository @Inject constructor(private val factory: PetsDataStoreFactory,
                                             private val petsMapper: PetMapper) :
        PetsRepository {
    override fun clearPets(): Completable {
        TODO("need implement cache")
    }

    override fun savePets(pets: List<Pet>): Completable {
        val petEntities = pets.map { petsMapper.mapToEntity(it) }
        return savePetEntities(petEntities)
    }

    override fun getPets(key: String, location: String, options: Map<String, String>):
            Single<List<Pet>> {
        val dataStore = factory.retriveDataStore()
        return dataStore.getPets(key, location, options)
                .flatMap {
                    if (dataStore is PetsRemoteDataStore) {
//                        savePetEntities(it).toSingle { it }
                        Single.just(it) // todo: delete after implement cache
                    } else {
                        Single.just(it)
                    }
                }
                .map { list ->
                    list.map { listItem ->
                        petsMapper.mapFromEntity(listItem)
                    }

                }
    }

    private fun savePetEntities(pets: List<PetEntity>): Completable {
        TODO("need implement cache")
    }
}
