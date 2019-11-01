package com.midwestpilotcars.dependencyInjection


import com.midwestpilotcars.MidwestPilotCars
import com.midwestpilotcars.constants.ApiConstants
import com.midwestpilotcars.helpers.Utils
import com.midwestpilotcars.network.NetworkRepository
import com.watcho.network.ApiService
import dagger.Module
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class DataModule(midwestPilotCars: MidwestPilotCars) {
    private var midwestPilotCars = midwestPilotCars
    @Provides
    @Singleton
    fun provideOkHTTPClient(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                    .addHeader(ApiConstants.KEY_CONTENT_TYPE, ApiConstants.CONTENT_TYPE)
            val request = requestBuilder.build()
            chain.proceed(request)
        }

        okHttpClient = httpClient.build()
        return okHttpClient!!
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideNetworkRepository(): NetworkRepository {
        return NetworkRepository(provideApiService(provideRetrofit(provideOkHTTPClient())))
    }

    companion object {
        private const val REQUEST_TIMEOUT: Long = 60
        private var okHttpClient: OkHttpClient? = null
    }
}
