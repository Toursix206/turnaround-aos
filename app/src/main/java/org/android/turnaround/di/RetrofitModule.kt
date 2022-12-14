package org.android.turnaround.di

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.android.turnaround.BuildConfig
import org.android.turnaround.R
import org.android.turnaround.data.local.datasource.LocalAuthPrefDataSource
import org.android.turnaround.data.remote.repository.RefreshRepositoryImpl.Companion.EXPIRED_REFRESH_TOKEN
import org.android.turnaround.data.remote.repository.RefreshRepositoryImpl.Companion.EXPIRED_TOKEN
import org.android.turnaround.domain.repository.RefreshRepository
import org.android.turnaround.presentation.intro.IntroActivity
import org.android.turnaround.util.ToastMessageUtil
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val HEADER_AUTHORIZATION = "Authorization"
    private const val HEADER_OS_TYPE = "TurnaroundOsType"
    private const val HEADER_VERSION = "TurnaroundVersion"
    private const val OS_TYPE = "AOS"
    private const val BEARER = "Bearer "

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NormalType

    @Provides
    @Singleton
    @NormalType
    fun providesInterceptor(
        @ApplicationContext context: Context,
        localPref: SharedPreferences,
        refreshRepository: RefreshRepository,
        localAuthPrefDataSource: LocalAuthPrefDataSource
    ): Interceptor =
        Interceptor { chain ->
            val request = chain.request()
            var response = chain.proceed(
                request
                    .newBuilder()
                    .addHeader(HEADER_AUTHORIZATION, BEARER + localAuthPrefDataSource.accessToken)
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
                                        .addHeader(HEADER_AUTHORIZATION, BEARER + localAuthPrefDataSource.accessToken)
                                        .addHeader(HEADER_OS_TYPE, OS_TYPE)
                                        .addHeader(HEADER_VERSION, BuildConfig.VERSION_NAME)
                                        .build()
                                )
                            }
                            .onFailure { throwable ->
                                Timber.d("토큰 갱신 실패 ${throwable.message}")
                                if (throwable is HttpException) {
                                    when (throwable.code()) {
                                        EXPIRED_REFRESH_TOKEN -> {
                                            with(localPref.edit()) {
                                                clear()
                                                commit()
                                            }
                                            Handler(Looper.getMainLooper()).post(
                                                Runnable {
                                                    ToastMessageUtil.showToast(
                                                        context,
                                                        context.getString(R.string.refresh_error)
                                                    )
                                                    context.startActivity(
                                                        Intent(context, IntroActivity::class.java).apply {
                                                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                    }
                }
            }
            response
        }

    @Provides
    @Singleton
    @NormalType
    fun providesOkHttpClient(@NormalType interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    @Provides
    @Singleton
    @NormalType
    fun providesRetrofit(@NormalType okHttpClient: OkHttpClient): Retrofit =
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
