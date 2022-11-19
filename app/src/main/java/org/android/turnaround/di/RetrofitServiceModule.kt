package org.android.turnaround.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.remote.service.*
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {
    @Provides
    @Singleton
    fun providesAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesTodoService(retrofit: Retrofit): TodoService =
        retrofit.create(TodoService::class.java)

    @Provides
    @Singleton
    fun providesHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun providesRoomService(retrofit: Retrofit): RoomService =
        retrofit.create(RoomService::class.java)

    @Provides
    @Singleton
    fun providesActivityService(retrofit: Retrofit): ActivityService =
        retrofit.create(ActivityService::class.java)

    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)
}
