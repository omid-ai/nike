package com.example.one.di

import com.example.one.model.TokenContainer
import com.example.one.util.Variables
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient):Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(Variables.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor {
                val oldRequest=it.request()
                val newRequestBuilder=oldRequest.newBuilder()
                if (!TokenContainer.token.isNullOrEmpty())
                    newRequestBuilder.addHeader("Authorization","Bearer ${TokenContainer.token}")
                newRequestBuilder.addHeader("Accept","application/json")
                    newRequestBuilder.method(oldRequest.method, oldRequest.body)
                return@addInterceptor it.proceed(newRequestBuilder.build())
            }.addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }
}