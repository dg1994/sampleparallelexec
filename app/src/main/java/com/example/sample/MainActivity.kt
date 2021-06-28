package com.example.sample

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.adapter.PostAdapter
import com.example.sample.di.ViewModelProviderFactory
import com.example.sample.network.Resource
import com.example.sample.viewmodel.UserViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var postAdapter: PostAdapter

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this, providerFactory).get(UserViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        userViewModel.posts.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    progress_bar_recycler.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    if (it.message?.isEmpty() == false) {
                        progress_bar_recycler.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        userViewModel.onPostErrorShown()
                    }
                }

                Resource.Status.SUCCESS -> {
                    progress_bar_recycler.visibility = View.GONE
                    it.data?.let { posts ->
                        postAdapter.addPosts(posts)
                    }
                }
            }
        })

        userViewModel.userChars.observe(this, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    progress_bar_char.visibility = View.VISIBLE
                }
                Resource.Status.ERROR -> {
                    if (it.message?.isEmpty() == false) {
                        progress_bar_char.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        userViewModel.onUserErrorShown()
                    }
                }

                Resource.Status.SUCCESS -> {
                    progress_bar_char.visibility = View.GONE
                    it.data?.let { numChars ->
                        val charCount = "$numChars chars "
                        char_count.text = charCount
                    }
                }
            }
        })

        userViewModel.totalTime.observe(this, Observer {
            val totalTime = "$it ms"
            response_time.text = totalTime
        })

        start_btn.setOnClickListener {
            response_time.text = ""
            char_count.text = ""
            userViewModel.fetchPostsAndUsers()
        }


    }

    private fun initRecyclerView() {
        posts_recycler.layoutManager = LinearLayoutManager(this)
        posts_recycler.adapter = postAdapter
        posts_recycler.itemAnimator = DefaultItemAnimator()
        posts_recycler.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                val spacing = 8
                outRect.top = spacing
                outRect.left = spacing
                outRect.right = spacing
                outRect.bottom = spacing
            }
        })
    }


}