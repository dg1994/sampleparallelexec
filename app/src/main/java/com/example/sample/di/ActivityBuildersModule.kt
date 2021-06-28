package com.example.sample.di

import com.example.sample.MainActivity
import com.example.sample.di.main.MainModule
import com.example.sample.di.main.MainScope
import com.example.sample.di.main.UserViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {
    @MainScope
    @ContributesAndroidInjector(modules = [UserViewModelModule::class, MainModule::class])
    abstract fun contributeMainActivity(): MainActivity
}