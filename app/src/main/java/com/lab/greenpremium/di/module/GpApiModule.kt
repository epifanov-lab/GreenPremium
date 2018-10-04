package com.lab.greenpremium.di.module

import com.lab.greenpremium.data.network.GpApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class GpApiModule {

    @Provides
    fun provideGreenPremiumApi(retrofit: Retrofit): GpApi {
        return retrofit.create(GpApi::class.java)
    }

}