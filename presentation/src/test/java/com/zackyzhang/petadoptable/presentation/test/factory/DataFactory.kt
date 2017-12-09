package com.zackyzhang.petadoptable.presentation.test.factory

import java.util.concurrent.ThreadLocalRandom

/**
 * Created by lei on 12/8/17.
 */
class DataFactory {

    companion object Factory {

        fun randomInt(): Int {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
        }

        fun randomLong(): Long {
            return randomInt().toLong()
        }

        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomIntInString(): String {
            return randomInt().toString()
        }
    }
}