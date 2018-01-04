package com.zackyzhang.petadoptable.cache.test.factory

import com.zackyzhang.petadoptable.cache.db.entity.petcache.*
import com.zackyzhang.petadoptable.cache.test.factory.DataFactory.Factory.randomLong
import com.zackyzhang.petadoptable.cache.test.factory.DataFactory.Factory.randomUuid
import com.zackyzhang.petadoptable.data.model.PetEntity

/**
 * Created by lei on 12/6/17.
 */
class PetFactory {

    companion object Factory {

        //<editor-fold desc="create PetDbEntity">
        fun makeCachedPet(): PetDbEntity {
            val medias = mutableListOf<String>()
            medias.add(DataFactory.randomUuid())
            medias.add(DataFactory.randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(DataFactory.randomUuid())
            breeds.add(DataFactory.randomUuid())

            return PetDbEntity(null, randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    medias, randomUuid(), breeds, randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid())
        }

        fun makeCachedPetList(count: Int): List<PetDbEntity> {
            val cachedPets = mutableListOf<PetDbEntity>()
            repeat(count) {
                cachedPets.add(makeCachedPet())
            }
            return cachedPets
        }

        fun makeMediaDbEntityList(count: Int): List<MediaDbEntity> {
            val mediaEntities = mutableListOf<MediaDbEntity>()
            repeat(count) {
                mediaEntities.add(makeMediaDbEntity())
            }
            return mediaEntities
        }

        fun makeBreedDbEntityList(count: Int): List<BreedDbEntity> {
            val mediaEntities = mutableListOf<BreedDbEntity>()
            repeat(count) {
                mediaEntities.add(makeBreedDbEntity())
            }
            return mediaEntities
        }

        fun makeMediaDbEntity(): MediaDbEntity {
            return MediaDbEntity(null, randomLong(), randomUuid())
        }

        fun makeBreedDbEntity(): BreedDbEntity {
            return BreedDbEntity(null, randomLong(), randomUuid())
        }
        //</editor-fold>

        //<editor-fold desc="create FavoritePetDbEntity">
        fun makeFavoritePetEntity(): FavoritePetDbEntity {
            val medias = mutableListOf<String>()
            medias.add(DataFactory.randomUuid())
            medias.add(DataFactory.randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(DataFactory.randomUuid())
            breeds.add(DataFactory.randomUuid())

            return FavoritePetDbEntity(null, randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    medias, randomUuid(), breeds, randomUuid(), randomUuid(), randomUuid(), randomUuid(),
                    randomUuid(), randomUuid(), randomUuid())

        }

        fun makeFavoritePetEntityList(count: Int): List<FavoritePetDbEntity> {
            val favoritePets = mutableListOf<FavoritePetDbEntity>()
            repeat(count) {
                favoritePets.add(makeFavoritePetEntity())
            }
            return favoritePets
        }

        fun makeFavoriteMediaDbEntityList(count: Int): List<FavoriteMediaDbEntity> {
            val mediaEntities = mutableListOf<FavoriteMediaDbEntity>()
            repeat(count) {
                mediaEntities.add(makeFavoriteMediaDbEntity())
            }
            return mediaEntities
        }

        fun makeFavoriteBreedDbEntityList(count: Int): List<FavoriteBreedDbEntity> {
            val mediaEntities = mutableListOf<FavoriteBreedDbEntity>()
            repeat(count) {
                mediaEntities.add(makeFavoriteBreedDbEntity())
            }
            return mediaEntities
        }

        fun makeFavoriteMediaDbEntity(): FavoriteMediaDbEntity {
            return FavoriteMediaDbEntity(null, randomLong(), randomUuid())
        }

        fun makeFavoriteBreedDbEntity(): FavoriteBreedDbEntity {
            return FavoriteBreedDbEntity(null, randomLong(), randomUuid())
        }
        //</editor-fold>

        //<editor-fold desc="create PetEntity">
        fun makePetEntity(): PetEntity {
            val medias = mutableListOf<String>()
            medias.add(DataFactory.randomUuid())
            medias.add(DataFactory.randomUuid())
            val breeds = mutableListOf<String>()
            breeds.add(DataFactory.randomUuid())
            breeds.add(DataFactory.randomUuid())

            return PetEntity(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    medias, DataFactory.randomUuid(), breeds, DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(),
                    DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makePetEntityList(count: Int): List<PetEntity> {
            val petEntities = mutableListOf<PetEntity>()
            repeat(count) {
                petEntities.add(makePetEntity())
            }
            return petEntities
        }

        //</editor-fold>

        fun makeStringList(count: Int): List<String> {
            val list = mutableListOf<String>()
            repeat(count) {
                list.add(randomUuid())
            }
            return list
        }



    }
}