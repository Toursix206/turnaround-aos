package org.android.turnaround.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.remote.repository.ActivityRepositoryImpl
import org.android.turnaround.data.remote.repository.AuthRepositoryImpl
import org.android.turnaround.data.remote.repository.HomeRepositoryImpl
import org.android.turnaround.data.remote.repository.RefreshRepositoryImpl
import org.android.turnaround.data.remote.repository.RoomRepositoryImpl
import org.android.turnaround.data.remote.repository.TodoRepositoryImpl
import org.android.turnaround.domain.repository.ActivityRepository
import org.android.turnaround.domain.repository.AuthRepository
import org.android.turnaround.domain.repository.HomeRepository
import org.android.turnaround.domain.repository.RefreshRepository
import org.android.turnaround.domain.repository.RoomRepository
import org.android.turnaround.domain.repository.TodoRepository
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
    fun providesRefreshRepository(refreshRepositoryImpl: RefreshRepositoryImpl): RefreshRepository =
        refreshRepositoryImpl

    @Provides
    @Singleton
    fun providesTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): TodoRepository =
        todoRepositoryImpl

    @Provides
    @Singleton
    fun providesHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository =
        homeRepositoryImpl

    @Provides
    @Singleton
    fun providesRoomRepository(roomRepositoryImpl: RoomRepositoryImpl): RoomRepository =
        roomRepositoryImpl

    @Provides
    @Singleton
    fun providesActivityRepository(activityRepositoryImpl: ActivityRepositoryImpl): ActivityRepository =
        activityRepositoryImpl
}
