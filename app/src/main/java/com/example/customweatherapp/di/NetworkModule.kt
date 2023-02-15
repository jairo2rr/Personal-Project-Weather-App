package com.example.customweatherapp.di

import androidx.lifecycle.SavedStateHandle
import com.example.customweatherapp.data.WeatherDbRepository
import com.example.customweatherapp.data.database.dao.CityItemDao
import com.example.customweatherapp.data.network.WeatherDbClient
import com.example.customweatherapp.data.network.WeatherDbService
import com.example.customweatherapp.domain.GetPrincipalDataUC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) //El alcance que tendra esta instancia
object NetworkModule {

    /*TODO: SEGUIR INVESTIGANDO A CERCA DE DAGGER HILT CON VIEW MODEL Y EL SAVEDSTATEHANDLE*/
    /*@Provides
    fun provideLatitude(stateHandle: SavedStateHandle):Double{
        return stateHandle.get<Double>("lat") ?: 0.0
    }*/
    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApiClient(retrofit: Retrofit):WeatherDbClient{
        return retrofit.create(WeatherDbClient::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherService(api:WeatherDbClient): WeatherDbService{
        return WeatherDbService(api)
    }

    @Provides
    @Singleton
    fun provideRepository(service:WeatherDbService, cityItemDao: CityItemDao):WeatherDbRepository{
        return WeatherDbRepository(service,cityItemDao)
    }

    @Provides
    @Singleton
    fun provideGetPrincipalData(repository: WeatherDbRepository): GetPrincipalDataUC{
        return GetPrincipalDataUC(repository)
    }
}