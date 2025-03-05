package com.example.myapplication.ui.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.usecase.CharacterResult
import com.example.myapplication.domain.usecase.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCountriesUseCase: GetCharacterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CharactersUiState.empty)
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    init {
        refreshData()
    }

    fun refreshData() {
        loadCharacters(forceRefresh = true)
    }

    private fun loadCharacters(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            getCountriesUseCase(forceRefresh).collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is CharacterResult.Success -> {
                            state.copy(
                                characters = result.countries,
                                isLoading = false,
                                errorHolder = ErrorHolder.initial
                            )
                        }
                        is CharacterResult.Error -> {
                            state.copy(
                                isLoading = false,
                                errorHolder = ErrorHolder(
                                    errorMessage = result.message,
                                    isError = true
                                )
                            )
                        }
                        CharacterResult.Loading -> {
                            CharactersUiState.loading
                        }
                    }
                }
            }
        }
    }

    fun onCharacterClick(id: Int) {
        _uiState.update { state ->
            val updatedCharacters = state.characters.map { character ->
                if (character.id == id) {
                    character.copy(showFullInfo = !character.showFullInfo)
                } else {
                    character
                }
            }
            state.copy(characters = updatedCharacters)
        }
    }

    fun dismissError() {
        _uiState.update { state ->
            state.copy(
                errorHolder = ErrorHolder.initial
            )
        }
        loadCharacters(forceRefresh = false)
    }

    fun retry() {
        _uiState.update { state ->
            state.copy(
                errorHolder = ErrorHolder.initial
            )
        }
        loadCharacters(forceRefresh = true)
    }
}