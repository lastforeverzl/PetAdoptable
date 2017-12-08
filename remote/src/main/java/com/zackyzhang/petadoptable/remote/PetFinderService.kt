package com.zackyzhang.petadoptable.remote

import com.zackyzhang.petadoptable.remote.model.GetPetResponse
import com.zackyzhang.petadoptable.remote.model.GetPetsResponse
import com.zackyzhang.petadoptable.remote.model.GetShelterResponse
import com.zackyzhang.petadoptable.remote.model.GetSheltersResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by lei on 11/29/17.
 */
interface PetFinderService {

    @GET("pet.find?format=json")
    fun getPets(@QueryMap options: Map<String, String>) : Flowable<GetPetsResponse>

    @GET("shelter.get?format=json")
    fun getShelterById(@QueryMap options: Map<String, String>) : Single<GetShelterResponse>

    @GET("shelter.find?format=json")
    fun getShelters(@QueryMap options: Map<String, String>) : Single<GetSheltersResponse>

    @GET("shelter.getPets?format=json")
    fun getShelterPets(@QueryMap options: Map<String, String>) :Single<GetPetsResponse>

    @GET("pet.get?format=json")
    fun getPetById(@QueryMap options: Map<String, String>) : Single<GetPetResponse>

}