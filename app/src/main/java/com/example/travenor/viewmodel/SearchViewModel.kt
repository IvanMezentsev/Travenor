package com.example.travenor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travenor.model.Place
import com.example.travenor.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = PlaceRepository()

    // Повний список (кеш для пошуку)
    private var allPlaces: List<Place> = emptyList()

    // Стан UI: список, який ми показуємо зараз
    private val _places = MutableStateFlow<List<Place>>(emptyList())
    val places: StateFlow<List<Place>> = _places.asStateFlow()

    init {
        loadPlaces()
    }

    private fun loadPlaces() {
        viewModelScope.launch {
            allPlaces = repository.getPlaces()
            _places.value = allPlaces
        }
    }

    // Логіка пошуку
    fun search(query: String) {
        if (query.isBlank()) {
            _places.value = allPlaces
        } else {
            val filtered = allPlaces.filter { place ->
                place.name.contains(query, ignoreCase = true) ||
                        place.location.contains(query, ignoreCase = true)
            }
            _places.value = filtered
        }
    }
}