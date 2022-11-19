package org.android.turnaround.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.android.turnaround.BuildConfig
import org.android.turnaround.data.local.datasource.LocalAuthPrefDataSource
import org.android.turnaround.data.remote.repository.RefreshRepositoryImpl.Companion.EXPIRED_TOKEN
import org.android.turnaround.domain.repository.RefreshRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val HEADER_OS_TYPE = "TurnaroundOsType"
    private const val HEADER_VERSION = "TurnaroundVersion"
    private const val OS_TYPE = "AOS"

    @Provides
    @Singleton
    fun providesInterceptor(
        refreshRepository: RefreshRepository,
        localAuthPrefDataSource: LocalAuthPrefDataSource
    ): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(
                request
                    .newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, localAuthPrefDataSource.accessToken)
                    .addHeader(HEADER_OS_TYPE, OS_TYPE)
                    .addHeader(HEADER_VERSION, BuildConfig.VERSION_NAME)
                    .build()
            )
            when (response.code) {
                EXPIRED_TOKEN -> {
                    runBlocking {
                        refreshRepository.refreshToken()
                            .onSuccess {
                                response = chain.proceed(
                                    request
                                        .newBuilder()
                                        .addHeader(
                                            HEADER_AUTHORIZATION,
                                            localAuthPrefDataSource.accessToken
                                        )
                                        .addHeader(HEADER_OS_TYPE, OS_TYPE)
                                        .addHeader(HEADER_VERSION, BuildConfig.VERSION_NAME)
                                        .build()
                                )
                            }
                            .onFailure { Timber.d("토큰 갱신 실패 ${it.message}") }
                    }
                }
            }
            response
        }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.HOST_URI)
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
}
