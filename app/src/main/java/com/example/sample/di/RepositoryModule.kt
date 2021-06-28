package com.example.sample.di

import com.example.sample.network.SampleApi
import com.example.sample.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(sampleApi: SampleApi): UserRepository {
        return UserRepository(sampleApi)
    }
}
