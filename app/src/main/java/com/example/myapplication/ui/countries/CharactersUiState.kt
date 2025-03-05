package com.example.myapplication.ui.countries

import com.example.myapplication.domain.model.CharacterUiModel

data class CharactersUiState(
    val characters: List<CharacterUiModel>,
    val isLoading: Boolean,
    val errorHolder: ErrorHolder
) {
    companion object {
        val empty = CharactersUiState(
            characters = emptyList(),
            isLoading = false,
            errorHolder = ErrorHolder.initial
        )
        val loading = CharactersUiState(
            characters = emptyList(),
            isLoading = true,
            errorHolder = ErrorHolder.initial
        )
    }
}

data class ErrorHolder(
    val errorMessage: String,
    val isError: Boolean
) {
    companion object {
        val initial = ErrorHolder(
            errorMessage = "",
            isError = false
        )
    }
}