package com.example.myapplication.di


import com.example.myapplication.data.repository.CharacterRepositoryImpl
import com.example.myapplication.data.source.local.CharacterDao
import com.example.myapplication.data.source.remote.RemoteDataSource
import com.example.myapplication.data.util.NetworkUtils
import com.example.myapplication.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideCharacterRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: CharacterDao,
        networkUtils: NetworkUtils
    ): CharacterRepository {
        return CharacterRepositoryImpl(remoteDataSource, localDataSource, networkUtils)
    }
}