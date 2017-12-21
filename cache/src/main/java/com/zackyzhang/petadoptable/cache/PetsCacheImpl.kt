package com.zackyzhang.petadoptable.cache

import com.zackyzhang.petadoptable.cache.db.PetAdoptableDatabase
import com.zackyzhang.petadoptable.cache.db.entity.BreedDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.MediaDbEntity
import com.zackyzhang.petadoptable.cache.db.entity.PetDbEntity
import com.zackyzhang.petadoptable.cache.mapper.PetEntityMapper
import com.zackyzhang.petadoptable.data.model.PetEntity
import com.zackyzhang.petadoptable.data.repository.PetsCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving Pets instances. This class implements the
 * [PetsCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class PetsCacheImpl @Inject constructor(private val petAdoptableDatabase: PetAdoptableDatabase,
                                        private val entityMapper: PetEntityMapper,
                                        private val preferencesHelper: PreferencesHelper) :
        PetsCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    /**
     * Remove all the data related to pet from tables in the database.
     */
    override fun clearPets(): Completable {
        return Completable.defer {
//            val allPets = petAdoptableDatabase.getPetDao().getAllPets()
//            allPets.forEach { pet -> petAdoptableDatabase.getPetDao().deletePet(pet) }
            petAdoptableDatabase.getPetDao().clearPets()
            println("PetsCacheImpl clearPets")
            Completable.complete()
        }
    }

    /**
     * Save the given list of [PetEntity] instances to the database.
     */
    override fun savePets(pets: List<PetEntity>): Completable {
        return Completable.defer {
            pets.forEach {
                val id = savePet(entityMapper.mapToCached(it))
                saveMedia(entityMapper.mapToCachedMedia(it.medias, id))
                saveBreed(entityMapper.mapToCachedBreed(it.breeds, id))
            }
            Completable.complete()
        }
    }

    /**
     * Helpter method for saving a [PetDbEntity] instance to the database.
     */
    private fun savePet(cachedPet: PetDbEntity): Long {
        return petAdoptableDatabase.getPetDao().insertPet(cachedPet)
    }

    private fun saveMedia(cachedMedias: List<MediaDbEntity>) {
        petAdoptableDatabase.getMediaDao().insertMedias(cachedMedias)
    }

    private fun saveBreed(cachedBreeds: List<BreedDbEntity>) {
        petAdoptableDatabase.getBreedDao().insertBreeds(cachedBreeds)
    }

    /**
     * Retrieve a list of [PetEntity] instances from the database.
     */
    override fun getPets(animal: String): Flowable<List<PetEntity>> {
//        return Flowable.defer<List<PetEntity>> {
//            val petEntities = mutableListOf<PetEntity>()
//            val pets = petAdoptableDatabase.getPetDao().getAllPets()
//            pets.forEach { pet ->
//                val petEntity = entityMapper.mapFromCached(pet)
//                val mediasForPet = petAdoptableDatabase.getMediaDao().getMediasForPet(pet.uid!!)
//                val breedsForPet = petAdoptableDatabase.getBreedDao().getBreedsForPet(pet.uid!!)
//                petEntity.medias = entityMapper.mapFromCachedMedia(mediasForPet)
//                petEntity.breeds = entityMapper.mapFromCachedBreed(breedsForPet)
//                petEntities.add(petEntity)
//            }
//            Flowable.just<List<PetEntity>>(petEntities)
//        }
        return Flowable.defer {
//            Flowable.just(petAdoptableDatabase.getPetDao().getAllPets())
            Flowable.just(petAdoptableDatabase.getPetDao().getAllPetsByAnimal(matchAnimalName(animal)))
        }.map {
            it.map {
                val mediasForPet = petAdoptableDatabase.getMediaDao().getMediasForPet(it.uid!!)
                val breedsForPet = petAdoptableDatabase.getBreedDao().getBreedsForPet(it.uid!!)
                it.medias = entityMapper.mapFromCachedMedia(mediasForPet)
                it.breeds = entityMapper.mapFromCachedBreed(breedsForPet)
                entityMapper.mapFromCached(it)
            }
        }
    }

    /**
     * Checked if there is any data in the cache
     */
    override fun isCached(animal: String): Single<Boolean> {
        return Single.defer {
            println("<PetsCacheImpl>animal isCached: " + animal)
            Single.just(petAdoptableDatabase.getPetDao()
                    .getAllPetsByAnimal(matchAnimalName(animal)).isNotEmpty())
        }
    }

//    override fun setCached() {
//        preferencesHelper.isCached = true
//    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

    private fun matchAnimalName(animal: String): String {
        return when (animal) {
            "smallfurry" -> "Small & Furry"
            "reptile" -> "Scales, Fins & Other"
            else -> animal
        }
    }
}