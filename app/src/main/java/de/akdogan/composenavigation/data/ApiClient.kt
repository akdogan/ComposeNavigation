package de.akdogan.composenavigation.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private val json by lazy { Json { ignoreUnknownKeys = true } }

private val contentType = "application/json".toMediaType()


/**
 * creates a retrofit api client
 * @param baseUrl the base url for this client
 * @param serviceClass the retrofit interface class
 * @param interceptors optional interceptors that should be added (as a list because maybe we want
 * to add more in the future)
 */
fun <T> getApiInstance(
    baseUrl: String,
    serviceClass: Class<T>,
    interceptors: List<Interceptor> = emptyList()
): T {

    return getBuilder(baseUrl, interceptors).create(serviceClass)
}

private fun getBuilder(baseUrl: String, interceptors: List<Interceptor>): Retrofit {
    val okHttpClient = OkHttpClient
        .Builder()
        .apply {
            interceptors.forEach {
                addInterceptor(it)
            }
        }.build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
}