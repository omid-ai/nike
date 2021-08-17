package com.example.one.di

import com.example.one.model.apiService.NikeApiService
import com.example.one.model.dataSource.BannerRemoteDataSource
import com.example.one.model.repository.BannerRepository
import com.example.one.model.repository.BannerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideBannerRepository(apiService: NikeApiService): BannerRepository {
        return BannerRepositoryImpl(BannerRemoteDataSource(apiService))
    }
}