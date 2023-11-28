package com.pierretest.gameshopapp.di

import android.app.Application
import androidx.room.Room
import com.pierretest.gameshopapp.common.ApiDetails
import com.pierretest.gameshopapp.data.database.AppDatabase
import com.pierretest.gameshopapp.data.database.GameDao
import com.pierretest.gameshopapp.data.remote.ApiCall
import com.pierretest.gameshopapp.data.repository.Repository
import com.pierretest.gameshopapp.data.repository.RepositoryImpl
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

class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpInstance() : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        client: OkHttpClient
    ) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiDetails.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideAPI(
        retrofit: Retrofit
    ) : ApiCall {
        return retrofit.create(ApiCall::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(
        apiCall: ApiCall,
        gameDao: GameDao
    ) : Repository {
        return RepositoryImpl(apiCall, gameDao)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application) : AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java, "games-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGameDao(appDatabase: AppDatabase): GameDao {
        return appDatabase.gameDao()
    }
}