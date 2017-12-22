package com.zackyzhang.petadoptable.ui.widget

import com.zackyzhang.petadoptable.ui.model.PetViewModel

/**
 * Created by lei on 12/20/17.
 */
object Utils {

    fun getPetInfo(pet: PetViewModel): String {
        val size = Constants.petSizeMap[pet.size]
        val age = pet.age
        val sex = Constants.petSexMap[pet.sex]
        val breed = getBreedString(pet)
        return String.format("%s - %s - %s - %s", size, age, sex, breed)
    }

    fun getBreedString(pet: PetViewModel): String {
        return pet.breeds.joinToString(separator = " & ")
    }

}