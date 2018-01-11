package com.zackyzhang.petadoptable.remote.model

data class Shelter(val country: Country?,
                   val longitude: Longitude?,
                   val name: Name?,
                   val phone: Phone?,
                   val state: State?,
                   val address2: Address2?,
                   val email: Email?,
                   val city: City?,
                   val zip: Zip?,
                   val fax: Fax?,
                   val latitude: Latitude?,
                   val id: Id?,
                   val address1: Address1?)

data class Country(val value: String)

data class Longitude(val value: String)

data class Phone(val value: String)

data class Address2(val value: String)

data class Email(val value: String)

data class Zip(val value: String)

data class Fax(val value: String)

data class Latitude(val value: String)

data class Address1(val value: String)