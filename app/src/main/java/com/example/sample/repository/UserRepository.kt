package com.example.sample.repository

import com.example.sample.network.SampleApi
import com.example.sample.network.model.Post
import javax.inject.Inject

class UserRepository @Inject constructor(val sampleApi: SampleApi) {

    suspend fun getPosts(): List<Post>? {
        return sampleApi.getPosts()
    }

    suspend fun getUserCharacters(): Int {
        return sampleApi.getUsers()?.string()?.length ?: 0
    }
}