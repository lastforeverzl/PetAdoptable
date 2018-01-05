package com.zackyzhang.petadoptable.ui.widget

import com.zackyzhang.petadoptable.ui.model.ShelterViewModel

/**
 * Created by lei on 1/3/18.
 */
interface ShelterOnClickListener {

    fun onClickShelter(shelter: ShelterViewModel)

    fun callShelter(number: String)

    fun directToShelter(lat: String, lng: String, address: String)

}