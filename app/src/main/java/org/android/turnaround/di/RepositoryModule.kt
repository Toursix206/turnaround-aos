package org.android.turnaround.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.remote.repository.*
import org.android.turnaround.domain.repository.*
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

    @Provides
    @Singleton
    fun providesUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl
}
