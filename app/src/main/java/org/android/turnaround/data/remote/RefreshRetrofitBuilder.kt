package org.android.turnaround.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.android.turnaround.BuildConfig
import org.android.turnaround.data.local.datasource.LocalAuthPrefDataSource
import org.android.turnaround.data.remote.service.RefreshService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RefreshRetrofitBuilder(localAuthPrefDataSource: LocalAuthPrefDataSource) {
    private val headerInterceptor = Interceptor { chain ->
        with(chain) {
            val request = chain.request()
            val response = chain.proceed(
                request
                    .newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, localAuthPrefDataSource.accessToken)
                    .addHeader(HEADER_OS_TYPE, OS_TYPE)
                    .addHeader(HEADER_VERSION, BuildConfig.VERSION_NAME)
                    .build()
            )
            response
        }
    }

    private val okHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.HOST_URI)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val refreshService: RefreshService = retrofit.create(RefreshService::class.java)

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val HEADER_OS_TYPE = "TurnaroundOsType"
        private const val HEADER_VERSION = "TurnaroundVersion"
        private const val OS_TYPE = "AOS"
    }
}
