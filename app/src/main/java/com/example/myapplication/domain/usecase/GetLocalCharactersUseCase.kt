package com.example.myapplication.domain.usecase

import com.example.myapplication.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

class GetLocalCharactersUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    operator fun invoke(): Flow<CharacterResult> {
        return flow {
            emit(CharacterResult.Loading)
            repository.getCharacters()
                .collect { countriesFlow ->
                    val characters = countriesFlow.getOrThrow()
                    emit(CharacterResult.Success(characters))
                }
        }.catch { e ->
            Timber.e("Catched an Error: $e")
            emit(CharacterResult.Error(e.message ?: "Unknown error"))
        }
    }
}