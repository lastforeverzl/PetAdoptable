package com.zackyzhang.petadoptable.remote.mapper

/**
 * Created by lei on 12/1/17.
 */
interface EntityMapper<in M, out E>{
    fun mapFromRemote(type: M): E
}