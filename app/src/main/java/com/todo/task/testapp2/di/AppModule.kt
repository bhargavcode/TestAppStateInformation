package com.todo.task.testapp2.di

import com.todo.task.testapp2.repository.StateRepository
import android.content.Context
import com.todo.task.testapp2.repository.StateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStateRepository(@ApplicationContext context: Context): StateRepository {
        return StateRepositoryImpl(context)
    }
}
