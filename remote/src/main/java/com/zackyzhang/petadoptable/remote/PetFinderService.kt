package com.zackyzhang.petadoptable.remote

import com.zackyzhang.petadoptable.remote.model.GetPetResponse
import com.zackyzhang.petadoptable.remote.model.GetPetsResponse
import com.zackyzhang.petadoptable.remote.model.GetShelterResponse
import com.zackyzhang.petadoptable.remote.model.GetSheltersResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by lei on 11/29/17.
 */
interface PetFinderService {

    @GET("pet.find?format=json")
    fun getPets(@Query("key") key: String,
                @Query("location") location: String,
                @QueryMap options: Map<String, String>) : Flowable<GetPetsResponse>

    @GET("shelter.get?format=json")
    fun getShelterById(@Query("key") key: String,
                       @Query("id") id: String) : Single<GetShelterResponse>

    @GET("shelter.find?format=json")
    fun getShelters(@Query("key") key: String,
                    @Query("location") location: String,
                    @QueryMap options: Map<String, String>) : Single<GetSheltersResponse>

    @GET("shelter.getPets?format=json")
    fun getShelterPets(@Query("key") key: String,
                       @Query("id") id: String,
                       @QueryMap options: Map<String, String>) :Single<GetPetsResponse>

    @GET("pet.get?format=json")
    fun getPetById(@Query("key") key: String,
                   @Query("id") id: String) : Single<GetPetResponse>

}