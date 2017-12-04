package com.zackyzhang.petadoptable.remote.test.factory

import java.util.concurrent.ThreadLocalRandom

/**
 * Created by lei on 12/3/17.
 */
class DataFactory {

    companion object factory {

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomIntInString(): String {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1).toString()
        }
    }
}