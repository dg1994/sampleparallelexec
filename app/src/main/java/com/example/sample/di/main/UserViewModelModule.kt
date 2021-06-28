package com.example.sample.di.main

import androidx.lifecycle.ViewModel
import com.example.sample.di.ViewModelKey
import com.example.sample.viewmodel.UserViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel::class)
    abstract fun bindUserViewModel(viewModel: UserViewModel): ViewModel
}