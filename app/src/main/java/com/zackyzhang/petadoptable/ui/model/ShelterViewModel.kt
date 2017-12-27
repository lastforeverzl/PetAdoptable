package com.zackyzhang.petadoptable.ui.model

/**
 * Representation for a [ShelterViewModel] fetched from an external layer data source
 */
//class ShelterViewModel(val country: String, val longitude: String, val name: String,
//                       val phone: String, val state: String, val address2: String, val email: String,
//                       val city: String, val zip: String, val fax: String, val latitude: String,
//                       val id: String, val address1: String)

class ShelterViewModel(val id: String, val name: String, val phone: String, val email: String,
                       val latitude: String, val longitude: String, val fax: String, val address: String)