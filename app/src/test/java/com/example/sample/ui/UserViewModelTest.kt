package com.example.sample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sample.MainCoroutineRule
import com.example.sample.getOrAwaitValueTest
import com.example.sample.network.Resource
import com.example.sample.repository.UserRepository
import com.example.sample.viewmodel.UserViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doThrow
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UserViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var repository: UserRepository

    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        viewModel = UserViewModel(repository)
    }

    @Test
    fun `when fetch posts should return success` () {
        mainCoroutineRule.runBlockingTest {
            `when`(repository.getPosts()).thenReturn(ArrayList())
            viewModel.fetchPostsAndUsers()
            val value = viewModel.posts.getOrAwaitValueTest()
            assertThat(value.status).isEqualTo(Resource.Status.SUCCESS)
        }
    }

    @Test
    fun `when fetch posts should return error` () {
        mainCoroutineRule.runBlockingTest {
            doThrow(RuntimeException("error message"))
                .`when`(repository)
                .getPosts()
            viewModel.fetchPostsAndUsers()
            val value = viewModel.posts.getOrAwaitValueTest()
            assertThat(value.status).isEqualTo(Resource.Status.ERROR)
        }
    }

    @Test
    fun `when fetch user characters, if count matches, return success` () {
        val numberOfCharacters = 1000
        mainCoroutineRule.runBlockingTest {
                    `when`(repository.getUserCharacters())
                            .thenReturn(numberOfCharacters)
            viewModel.fetchPostsAndUsers()
            val value = viewModel.userChars.getOrAwaitValueTest()
            assertThat(value.data).isEqualTo(numberOfCharacters)
        }
    }

    @Test
    fun `when fetch user characters, if count is null, return error` () {
        val numberOfCharacters : Int? = null
        mainCoroutineRule.runBlockingTest {
            `when`(repository.getUserCharacters())
                    .thenReturn(numberOfCharacters)
            viewModel.fetchPostsAndUsers()
            val value = viewModel.userChars.getOrAwaitValueTest()
            assertThat(value.status).isEqualTo(Resource.Status.ERROR)
        }
    }


    @After
    fun tearDown() {
    }
}