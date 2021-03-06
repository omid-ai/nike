package com.example.one.di

import android.content.Context
import android.content.SharedPreferences
import android.provider.DocumentsContract
import androidx.room.Room
import at.favre.lib.armadillo.Armadillo
import com.example.one.model.apiService.NikeApiService
import com.example.one.model.dataBase.FavoriteProductDB
import com.example.one.model.dataBase.FavoriteProductDao
import com.example.one.service.FrescoImageLoadingServiceImpl
import com.example.one.service.ImageLoadingService
import com.example.one.util.Variables
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit):NikeApiService{
        return retrofit.create(NikeApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideImageLoadingService():ImageLoadingService{
        return FrescoImageLoadingServiceImpl()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ):SharedPreferences{
        return Armadillo.create(context,Variables.SHARED_PREFERENCES_NAME)
            .encryptionFingerprint(context)
            .build()
    }

    @Singleton
    @Provides
    fun provideDataBase(
        @ApplicationContext context: Context
    ):FavoriteProductDB{
        return Room.databaseBuilder(context,FavoriteProductDB::class.java,"nike_db").build()
    }

    @Singleton
    @Provides
    fun provideDao(favoriteProductDB: FavoriteProductDB):FavoriteProductDao{
        return favoriteProductDB.getDao()
    }
}