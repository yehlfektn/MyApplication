package com.example.myapplication.data.source.remote


import com.example.myapplication.data.model.CharacterResponseDto
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("api/character")
    suspend fun getAllCharacter(): CharacterResponseDto

    // https://rickandmortyapi.com/api/character/
}