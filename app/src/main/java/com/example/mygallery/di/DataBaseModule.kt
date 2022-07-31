package com.example.mygallery.di

import android.content.Context
import androidx.room.Room
import com.example.mygallery.data.cache.CacheDataSource
import com.example.mygallery.data.cache.PictureDao
import com.example.mygallery.data.cache.PictureDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): PictureDataBase {
        return Room.databaseBuilder(
            context,
            PictureDataBase::class.java,
            PictureDataBase.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePictureDao(db: PictureDataBase): PictureDao {
        return db.pictureDatabaseDao()
    }

    @Provides
    @Singleton
    fun provideCacheDataSource(dao: PictureDao): CacheDataSource {
        return CacheDataSource(dao)
    }
}