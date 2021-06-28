package com.example.sample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sample.network.Resource
import com.example.sample.network.model.Post
import com.example.sample.repository.UserRepository
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    private val _userChars: MutableLiveData<Resource<Int?>> = MutableLiveData()
    val userChars: LiveData<Resource<Int?>>
        get() = _userChars

    private val _posts: MutableLiveData<Resource<List<Post>?>> = MutableLiveData()
    val posts: LiveData<Resource<List<Post>?>>
        get() = _posts

    private val _totalTime: MutableLiveData<Long> = MutableLiveData()
    val totalTime: LiveData<Long>
        get() = _totalTime


    //val scope = CoroutineScope(SupervisorJob())

    /*
     * fetches posts and users parallelly.
     * 1. Sets value of posts and userChars liveData to Resource.Loading(null)
     * 2. Makes API call. On successful response, sets the value of respective liveData to Resource.Success(value).
     * On error, sets the value of respective liveData to Resource.Error(msg).
     * 3. Calculate total time and sets the value of totalTime liveData.
     */

    fun fetchPostsAndUsers() {
         viewModelScope.launch {
             _posts.value = Resource.loading(null)
             _userChars.value = Resource.loading(null)
             supervisorScope {
                 val time = measureTimeMillis {
                     val postsDeferred = async { repository.getPosts() }
                     val usersDeferred = async { repository.getUserCharacters() }

                     val postsList: List<Post>? = try {
                         postsDeferred.await()
                     } catch (e: Exception) {
                         null
                     }

                     val usersCharCount: Int? = try {
                         usersDeferred.await()
                     } catch (e: Exception) {
                         null
                     }

                     _posts.value = if (postsList != null) {
                         Resource.success(postsList)
                     } else {
                         Resource.error("Error in fetching posts", null)
                     }

                     _userChars.value = if (usersCharCount != null) {
                         Resource.success(usersCharCount)
                     } else {
                         Resource.error("Error in fetching users", null)
                     }
                 }
                 _totalTime.value = time
             }
         }
    }

    fun onPostErrorShown() {
        _posts.value = Resource.error("", null)
    }

    fun onUserErrorShown() {
        _userChars.value = Resource.error("", null)
    }
}