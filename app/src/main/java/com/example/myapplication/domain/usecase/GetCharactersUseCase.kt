package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.model.CharacterUiModel
import com.example.myapplication.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(forceRefresh: Boolean = false): Flow<CharacterResult> {
        return flow {
            emit(CharacterResult.Loading)
            if (forceRefresh) {
                val result = repository.refreshCharacters()
                if (result.isFailure) {
                    emit(CharacterResult.Error(result.exceptionOrNull()?.message ?: "Unknown error"))
                    return@flow
                }
            }
            emit(CharacterResult.Loading)

            repository.getCharacters()
                .collect { charactersFlow ->
                    val characters = charactersFlow.getOrThrow()
                    emit(CharacterResult.Success(characters))
                }
        }.catch { e ->
            Timber.e("Catched an Error: $e")
            emit(CharacterResult.Error(e.message ?: "Unknown error"))
        }
    }
}

sealed class CharacterResult {
    data object Loading : CharacterResult()
    data class Success(val countries: List<CharacterUiModel>) : CharacterResult()
    data class Error(val message: String) : CharacterResult()
}