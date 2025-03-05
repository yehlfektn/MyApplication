package com.example.myapplication.data.source.remote


import com.example.myapplication.data.model.CharacterResponseDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
) {
    suspend fun getCharacters(): CharacterResponseDto = rickAndMortyApi.getAllCharacter()
}