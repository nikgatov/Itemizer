package com.example.itemizer.data

import com.example.itemizer.model.Item
import com.example.itemizer.model.Section
import com.example.itemizer.network.ItemizerApiService

interface ItemizerRepository {
    suspend fun getItems(): List<Section>
}

//Business logic
class DefaultItemizerRepository(
    private val itemizerApiService: ItemizerApiService
) : ItemizerRepository {
    override suspend fun getItems(): List<Section> {
        var mapOflists = itemizerApiService.getItems().sortedBy { it.listId }.groupBy { it.listId }

        var sections = mutableListOf<Section>()

        for((key, value) in mapOflists) {
            val newList = mutableListOf<Item>()
            value.sortedBy { it.name }.filterNotTo(newList) {item -> item.name.isNullOrEmpty()}
            sections.add(Section(key, newList))
        }
        return sections
    }
}