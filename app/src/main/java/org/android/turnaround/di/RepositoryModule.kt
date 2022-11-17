package org.android.turnaround.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.remote.repository.ActivityRepository
import org.android.turnaround.data.remote.repository.ActivityRepositoryImpl
import org.android.turnaround.data.remote.repository.AuthRepository
import org.android.turnaround.data.remote.repository.AuthRepositoryImpl
import org.android.turnaround.data.remote.repository.RoomRepository
import org.android.turnaround.data.remote.repository.RoomRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providesAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository =
        authRepositoryImpl

    @Provides
    @Singleton
    fun providesRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository =
        roomRepositoryImpl

    @Provides
    @Singleton
    fun providesActivityRepository(activityRepositoryImpl: ActivityRepositoryImpl): ActivityRepository =
        activityRepositoryImpl
}
