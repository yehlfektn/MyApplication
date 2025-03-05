package com.example.myapplication.data.repository

import com.example.myapplication.data.model.CharacterDto
import com.example.myapplication.data.source.local.CharacterDao
import com.example.myapplication.data.source.local.CharacterEntity
import com.example.myapplication.data.source.remote.RemoteDataSource
import com.example.myapplication.data.util.NetworkUtils
import com.example.myapplication.domain.model.CharacterUiModel
import com.example.myapplication.domain.model.Location
import com.example.myapplication.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import timber.log.Timber

class CharacterRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: CharacterDao,
    private val networkUtils: NetworkUtils
) : CharacterRepository {

    override fun getCharacters(): Flow<Result<List<CharacterUiModel>>> {
        return localDataSource.getAllCharacters().map { entities ->
            if (entities.isEmpty()) {
                Result.success(emptyList())
            } else {
                Result.success(entities.map { it.toDomain() })
            }
        }
    }

    override suspend fun refreshCharacters(): Result<Unit> {
        return try {
            if (networkUtils.isNetworkAvailable()) {
                val remoteCharacters = remoteDataSource.getCharacters()
                val entities = remoteCharacters.results.map { it.toEntity() }
                localDataSource.insertCharacters(entities)
                Result.success(Unit)
            } else {
                Result.failure(Exception("No internet connection"))
            }
        } catch (e: Exception) {
            Timber.e(e, "Error refreshing countries")
            Result.failure(e)
        }
    }

    private fun CharacterEntity.toDomain(): CharacterUiModel {
        return CharacterUiModel(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = Location(
                name = originName,
                url = originUrl
            ),
            location = Location(
                name = locationName,
                url = locationUrl
            ),
            image = image,
            url = url,
            created = created
        )
    }

    private fun CharacterDto.toEntity(): CharacterEntity {
        return CharacterEntity(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            originName = origin.name,
            originUrl = origin.url,
            locationName = location.name,
            locationUrl = location.url,
            image = image,
            url = url,
            created = created
        )
    }
}