package com.example.myapplication.domain.model

data class CharacterUiModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val url: String,
    val created: String,
    val showFullInfo: Boolean = false
)

data class Location(
    val name: String,
    val url: String
)