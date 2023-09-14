package com.example.itemizer.model

import kotlinx.serialization.Serializable

@Serializable
data class Item (
    val id: Int,
    val listId: Int,
    val name: String?
)

data class Section (
    val listId: Int = 0,
    val data: MutableList<Item> = mutableListOf()
)