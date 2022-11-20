package org.android.turnaround.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.remote.service.ActivityService
import org.android.turnaround.data.remote.service.AuthService
import org.android.turnaround.data.remote.service.HomeService
import org.android.turnaround.data.remote.service.RefreshService
import org.android.turnaround.data.remote.service.RoomService
import org.android.turnaround.data.remote.service.TodoService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitServiceModule {
    @Provides
    @Singleton
    fun providesAuthService(@RetrofitModule.NormalType retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesRefreshService(@RefreshRetrofitModule.RefreshType retrofit: Retrofit): RefreshService =
        retrofit.create(RefreshService::class.java)

    @Provides
    @Singleton
    fun providesTodoService(@RetrofitModule.NormalType retrofit: Retrofit): TodoService =
        retrofit.create(TodoService::class.java)

    @Provides
    @Singleton
    fun providesHomeService(@RetrofitModule.NormalType retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    @Singleton
    fun providesRoomService(@RetrofitModule.NormalType retrofit: Retrofit): RoomService =
        retrofit.create(RoomService::class.java)

    @Provides
    @Singleton
    fun providesActivityService(@RetrofitModule.NormalType retrofit: Retrofit): ActivityService =
        retrofit.create(ActivityService::class.java)
}
