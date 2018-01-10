package com.zackyzhang.petadoptable.cache

import com.zackyzhang.petadoptable.cache.db.PetAdoptableDatabase
import com.zackyzhang.petadoptable.cache.db.entity.petcache.*
import com.zackyzhang.petadoptable.cache.mapper.FavoriteEntityMapper
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
                                        private val favoriteMapper: FavoriteEntityMapper,
                                        private val preferencesHelper: PreferencesHelper) :
        PetsCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    /**
     * Remove all the data related to pet from tables in the database.
     */
    override fun clearPets(): Completable {
        return Completable.defer {
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
     * Helper method for saving a [PetDbEntity] instance to the database.
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
        return Flowable.defer {
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
     * Retrieve a list of [PetEntity] instances for favorite pets from the database
     */
    override fun getFavoritePets(): Flowable<List<PetEntity>> {
        return Flowable.defer {
            Flowable.just(petAdoptableDatabase.getPetDao().getFavoritePets())
        }.map {
            it.map {
                val mediasForPet = petAdoptableDatabase.getMediaDao().getFavoriteMediasForPet(it.uid!!)
                val breedsForPet = petAdoptableDatabase.getBreedDao().getFavoriteBreedsForPet(it.uid!!)
                it.medias = favoriteMapper.mapFromCachedMedia(mediasForPet)
                it.breeds = favoriteMapper.mapFromCachedBreed(breedsForPet)
                favoriteMapper.mapFromCached(it)
            }
        }
    }

    override fun saveFavoritePet(pet: PetEntity): Completable {
        return Completable.defer {
            val id = saveFavoritePet(favoriteMapper.mapToCached(pet))
            saveFavoriteMedia(favoriteMapper.mapToCachedMedia(pet.medias, id))
            saveFavoriteBreed(favoriteMapper.mapToCachedBreed(pet.breeds, id))
            Completable.complete()
        }
    }

    override fun removeFavoritePet(pet: PetEntity): Completable {
        return Completable.defer {
            val favoritePetDbEntity = favoriteMapper.mapToCached(pet)
            val uid = petAdoptableDatabase.getPetDao().getFavoritePetById(pet.id).uid
            favoritePetDbEntity.uid = uid
            petAdoptableDatabase.getPetDao().removeFavoritePet(favoritePetDbEntity)
            val test = petAdoptableDatabase.getPetDao().getFavoritePetById(pet.id)
            println("test after remove favorite: $test")
            Completable.complete()
        }
    }

    override fun isFavoritePet(id: String): Single<Boolean> {
        return Single.defer {
            val pet = petAdoptableDatabase.getPetDao().getFavoritePetById(id)
            pet?.let {
                println("isFavoritePet: $pet")
                Single.just(true)
            } ?:run {
                println("isFavoritePet: is null")
                Single.just(false)
            }
        }
    }

    /**
     * Helper method for saving a [FavoritePetDbEntity] instance to the database.
     */
    private fun saveFavoritePet(cachedPet: FavoritePetDbEntity): Long {
        return petAdoptableDatabase.getPetDao().insertFavoritePet(cachedPet)
    }

    private fun saveFavoriteMedia(cachedMedias: List<FavoriteMediaDbEntity>) {
        petAdoptableDatabase.getMediaDao().insertFavoriteMedias(cachedMedias)
    }

    private fun saveFavoriteBreed(cachedBreeds: List<FavoriteBreedDbEntity>) {
        petAdoptableDatabase.getBreedDao().insertFavoriteBreeds(cachedBreeds)
    }

    /**
     * Update pet
     */
//    override fun updatePet(pet: PetEntity): Completable {
//        return Completable.defer {
//            val uid = petAdoptableDatabase.getPetDao().getPetById(pet.id).uid
//            println("updatePet uid: $uid")
//            val petDbEntity = entityMapper.mapToCached(pet)
//            petDbEntity.uid = uid
//            println("petDbEntity: $petDbEntity")
//            petAdoptableDatabase.getPetDao().updatePet(petDbEntity)
//            Completable.complete()
//        }
//    }

    /**
     * Retrieve a pet by id
     */
    override fun getPetById(id: String): Single<PetEntity> {
        return Single.defer {
            Single.just(petAdoptableDatabase.getPetDao().getPetById(id))
        }.map {
            val mediasForPet = petAdoptableDatabase.getMediaDao().getMediasForPet(it.uid!!)
            val breedsForPet = petAdoptableDatabase.getBreedDao().getBreedsForPet(it.uid!!)
            it.medias = entityMapper.mapFromCachedMedia(mediasForPet)
            it.breeds = entityMapper.mapFromCachedBreed(breedsForPet)
            entityMapper.mapFromCached(it)
        }
    }

    /**
     * Checked if there is any data in the cache
     */
    override fun isCached(animal: String): Single<Boolean> {
        return Single.defer {
            println("<PetsCacheImpl>animal isCached: $animal")
            Single.just(petAdoptableDatabase.getPetDao()
                    .getAllPetsByAnimal(matchAnimalName(animal)).isNotEmpty())
        }
    }

    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.petsLastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        println("$currentTime - $lastUpdateTime = ${currentTime - lastUpdateTime}, EXPIRATION_TIME = $EXPIRATION_TIME")
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.petsLastCacheTime
    }

    private fun matchAnimalName(animal: String): String {
        return when (animal) {
            "smallfurry" -> "Small & Furry"
            "reptile" -> "Scales, Fins & Other"
            else -> animal
        }
    }
}