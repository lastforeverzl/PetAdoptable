package com.zackyzhang.petadoptable.remote.model

data class Header(val status: Status)

data class Status(val code: Code)

data class Code(val value: String)