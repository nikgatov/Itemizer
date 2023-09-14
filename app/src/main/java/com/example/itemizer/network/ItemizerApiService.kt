package com.example.itemizer.network

import com.example.itemizer.model.Item
import retrofit2.http.GET

interface ItemizerApiService {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
}