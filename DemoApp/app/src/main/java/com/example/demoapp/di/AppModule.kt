package com.example.demoapp.di

import android.content.Context
import com.bumptech.glide.Glide
import com.example.demoapp.BuildConfig
import com.example.demoapp.data.api.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val API_KEY = "api_key"

    @Provides
    @Singleton
    fun providesMovieService(client: OkHttpClient): MovieService {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).
               client(client).addConverterFactory(GsonConverterFactory.create()).
               build().create(MovieService::class.java)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().
               addInterceptor(getInterceptor()).build()
    }

    @Provides
    @Singleton
    fun providesGlide(@ApplicationContext context: Context) = Glide.with(context)

    @Provides
    @Singleton
    @IoDispatcher
    fun providesIoDispatcher() = Dispatchers.IO

    private fun getInterceptor() = Interceptor { chain->
        val request = chain.request()
        val newRequest = request.url.newBuilder().
            addQueryParameter(API_KEY, BuildConfig.TEST_API_KEY).
            build()
        chain.proceed(request.newBuilder().
        url(newRequest).build())
    }
}