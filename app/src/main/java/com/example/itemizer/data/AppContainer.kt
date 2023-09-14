package com.example.itemizer.data

import com.example.itemizer.network.ItemizerApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val itemizerRepository: ItemizerRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://fetch-hiring.s3.amazonaws.com/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: ItemizerApiService by lazy {
        retrofit.create(ItemizerApiService::class.java)
    }

    /**
     * DI implementation for Itemizer repository
     */
    override val itemizerRepository: ItemizerRepository by lazy {
        DefaultItemizerRepository(retrofitService)
    }
}