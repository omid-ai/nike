package com.example.one.di

import com.example.one.model.apiService.NikeApiService
import com.example.one.model.dataSource.banner.BannerRemoteDataSource
import com.example.one.model.dataSource.comment.CommentRemoteDataSource
import com.example.one.model.dataSource.product.ProductRemoteDataSource
import com.example.one.model.repository.banner.BannerRepository
import com.example.one.model.repository.banner.BannerRepositoryImpl
import com.example.one.model.repository.comment.CommentRepository
import com.example.one.model.repository.comment.CommentRepositoryImpl
import com.example.one.model.repository.product.ProductRepository
import com.example.one.model.repository.product.ProductRepositoryImpl
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

    @Singleton
    @Provides
    fun provideProductRepository(apiService: NikeApiService):ProductRepository{
        return ProductRepositoryImpl(ProductRemoteDataSource(apiService))
    }

    @Singleton
    @Provides
    fun provideCommentRepository(apiService: NikeApiService):CommentRepository{
        return CommentRepositoryImpl(CommentRemoteDataSource(apiService))
    }
}