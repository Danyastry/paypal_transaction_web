package com.dvstry.paypalweb.di

import com.dvstry.paypalweb.Cmn.BASE_URL
import com.dvstry.paypalweb.Cmn.CLIENT_ID
import com.dvstry.paypalweb.Cmn.SECRET_ID
import com.dvstry.paypalweb.remote.PayPalApi
import com.dvstry.paypalweb.rep.PayPalRepository
import com.dvstry.paypalweb.rep.PayPalRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesPayPalApi(retrofit: Retrofit): PayPalApi {
        return retrofit.create(PayPalApi::class.java)
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30L, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun providesPayPalRepository(payPalApi: PayPalApi): PayPalRepository {
        return PayPalRepositoryImpl(payPalApi, CLIENT_ID, SECRET_ID)
    }

}