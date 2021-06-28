package com.example.sample.network

import com.example.sample.network.model.Post
import okhttp3.ResponseBody
import retrofit2.http.GET

interface SampleApi {
    @GET("/posts")
    suspend fun getPosts(): List<Post>?

    @GET("/users")
    suspend fun getUsers(): ResponseBody?
}