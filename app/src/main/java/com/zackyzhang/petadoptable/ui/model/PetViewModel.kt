package com.zackyzhang.petadoptable.ui.model

import com.zackyzhang.petadoptable.ui.widget.Constants
import java.text.SimpleDateFormat
import java.util.*

/**
 * Representation for a [PetViewModel] fetched from an external layer data source
 */
class PetViewModel(val status: String, val cityState: String, val age: String, val size: String,
                   val medias: List<String>, val id: String, val breeds: List<String>,
                   val name: String, val sex: String, val description: String, val mix: String,
                   val shelterId: String, val lastUpdate: String, val animal: String,
                   val isFavorite: Boolean = false) {

    fun getPetInfo(): String {
        val size = Constants.petSizeMap[this.size]
        val age = this.age
        val sex = Constants.petSexMap[this.sex]
        val breed = getBreedString()
        return "$size - $age - $sex - $breed"
    }

    private fun getBreedString(): String {
        return this.breeds.joinToString(separator = " & ")
    }

    fun getAdoptionStatus(): String? {
        return Constants.petStatusMap[this.status]
    }

    fun getLastUpdateInfo(): String? {
        val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val newDf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return newDf.format(df.parse(this.lastUpdate))
    }

}