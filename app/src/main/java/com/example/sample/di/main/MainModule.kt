package com.example.sample.di.main

import com.example.sample.adapter.PostAdapter
import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @MainScope
    @Provides
    fun providePostsRecyclerAdapter(): PostAdapter {
        return PostAdapter()
    }
}