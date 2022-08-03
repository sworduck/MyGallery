package com.example.mygallery.di

import com.example.mygallery.data.cloud.ApiService
import com.example.mygallery.data.cloud.CloudDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCloudDataSource(apiService: ApiService): CloudDataSource {
        return CloudDataSource(apiService)
    }

    /*
    @Provides
    @Singleton
    fun providesHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        val client = OkHttpClient.Builder()
        client.readTimeout(60, TimeUnit.SECONDS)
        client.writeTimeout(60, TimeUnit.SECONDS)
        client.connectTimeout(60, TimeUnit.SECONDS)
        client.addInterceptor(loggingInterceptor)

        return client.build()
    }

     */
}