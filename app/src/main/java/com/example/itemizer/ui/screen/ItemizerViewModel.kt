package com.example.itemizer.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.itemizer.ItemizerApplication
import com.example.itemizer.data.ItemizerRepository
import com.example.itemizer.model.Section
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface ItemizerUiState {
    data class Success(
        //constructor
        val items: List<Section>
    ) : ItemizerUiState
    data object Error : ItemizerUiState
    data object Loading : ItemizerUiState
}

/**
 * ViewModel containing ready-to-go data for displaying and method to retrieve the data
 */
class ItemizerViewModel(private val itemizerRepository: ItemizerRepository) : ViewModel() {
    var itemizerUiState: ItemizerUiState by mutableStateOf(ItemizerUiState.Loading)
        private set
    init {
        getItems()
    }
    fun getItems() {
        viewModelScope.launch {
            itemizerUiState = ItemizerUiState.Loading
            itemizerUiState = try {
                ItemizerUiState.Success(itemizerRepository.getItems())
            } catch (e: IOException) {
                ItemizerUiState.Error
            } catch (e: HttpException) {
                ItemizerUiState.Error
            }
        }
    }

    /**
     * Factory for [ItemizerViewModel] that takes [ItemizerRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val itemizerRepository = (this[APPLICATION_KEY] as ItemizerApplication).container.itemizerRepository
                ItemizerViewModel(itemizerRepository = itemizerRepository)
            }
        }
    }
}