package com.demo.demo.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.demo.demo.room.AppDatabase
import com.demo.demo.room.ShoppingDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "tummoc_database"
        ).allowMainThreadQueries().build()

    }

    @Provides
    fun provideYourDao(appDatabase: AppDatabase): ShoppingDataDao {
        return appDatabase.shoppingDataDao()
    }

}


