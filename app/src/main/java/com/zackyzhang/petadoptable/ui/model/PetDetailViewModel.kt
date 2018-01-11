package com.zackyzhang.petadoptable.ui.model

import com.zackyzhang.petadoptable.ui.widget.Constants
import java.text.SimpleDateFormat
import java.util.*

/**
 * Representation for a [PetViewModel] fetched from an external layer data source
 */
class PetDetailViewModel(val petStatus: String, val petCityState: String, val petAge: String, val petSize: String,
                         val petMedias: List<String>, val petId: String, val petBreeds: List<String>,
                         val petName: String, val petSex: String, val petDescription: String,
                         val petMix: String, val petShelterId: String, val petLastUpdate: String,
                         val petAnimal: String, var petIsFavorite: Boolean = false,
                         val shelterName: String, val shelterPhone: String, val shelterEmail: String,
                         val shelterLongitude: String, val shelterLatitude: String, val shelterId: String,
                         val shelterAddress: String) {

    fun getPetInfo(): String {
        val size = Constants.petSizeMap[this.petSize]
        val age = this.petAge
        val sex = Constants.petSexMap[this.petSex]
        val breed = getBreedString()
        return "$size - $age - $sex - $breed"
    }

    private fun getBreedString(): String {
        return this.petBreeds.joinToString(separator = " & ")
    }

    fun getAdoptionStatus(): String? {
        return Constants.petStatusMap[this.petStatus]
    }

    fun getLastUpdateInfo(): String? {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val newDf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return newDf.format(df.parse(this.petLastUpdate))
    }

}