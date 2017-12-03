package com.zackyzhang.petadoptable.remote.model

/**
 * Created by lei on 11/29/17.
 */
data class Header(val status: Status)

data class Status(val code: Code)

data class Code(val value: String)