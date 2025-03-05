package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.CharacterUiModel
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getCharacters(): Flow<Result<List<CharacterUiModel>>>
    suspend fun refreshCharacters(): Result<Unit>
}