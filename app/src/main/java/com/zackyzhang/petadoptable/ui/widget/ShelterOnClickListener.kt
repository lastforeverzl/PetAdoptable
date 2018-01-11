package com.zackyzhang.petadoptable.ui.widget

import com.zackyzhang.petadoptable.ui.model.ShelterViewModel

interface ShelterOnClickListener {

    fun onClickShelter(shelter: ShelterViewModel)

    fun callShelter(number: String)

    fun directToShelter(lat: String, lng: String, address: String)

}