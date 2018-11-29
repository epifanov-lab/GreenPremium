package com.lab.greenpremium.di.module


import com.lab.greenpremium.TEST_URL
import com.lab.greenpremium.data.network.GpInterceptor
import com.lab.greenpremium.utills.LogUtil
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(TEST_URL)
                .client(OkHttpClient.Builder()
                        .addInterceptor(GpInterceptor())
                        .addInterceptor(HttpLoggingInterceptor(LogUtil::d).setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}