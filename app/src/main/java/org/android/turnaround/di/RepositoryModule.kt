package org.android.turnaround.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.turnaround.data.remote.repository.AuthRepository
import org.android.turnaround.data.remote.repository.AuthRepositoryImpl
import org.android.turnaround.data.remote.repository.TodoRepository
import org.android.turnaround.data.remote.repository.TodoRepositoryImpl
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
    fun providesTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): TodoRepository =
        todoRepositoryImpl
}
