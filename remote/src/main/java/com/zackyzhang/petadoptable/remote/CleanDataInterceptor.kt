package com.zackyzhang.petadoptable.remote

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * Created by lei on 11/30/17.
 */
class CleanDataInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        val responseBuilder = originalResponse.newBuilder()
        if (originalResponse.isSuccessful) {
            val rawJson = originalResponse.body()!!.string()
            val cleanedJson = ApiUtils.cleanInvalidSymbol(rawJson)
            val breedFixedJson = ApiUtils.fixBreedObject(cleanedJson)
            val contentType = originalResponse.body()!!.contentType()
            val body = ResponseBody.create(contentType, breedFixedJson)
            responseBuilder.body(body)
        }
        return responseBuilder.build()
    }
}