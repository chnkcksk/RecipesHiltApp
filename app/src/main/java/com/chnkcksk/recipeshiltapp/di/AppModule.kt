package com.chnkcksk.recipeshiltapp.di

import com.chnkcksk.recipeshiltapp.repository.RecipeRepository
import com.chnkcksk.recipeshiltapp.service.RecipeApi
import com.chnkcksk.recipeshiltapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRecipeRepository(
        api: RecipeApi
    ) = RecipeRepository(api)

    @Singleton
    @Provides
    fun provideRecipeApi(): RecipeApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RecipeApi::class.java)

    }

}