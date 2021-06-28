package com.example.sample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.network.model.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private val postsList: ArrayList<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postsList[position]
        holder.title.text = post.title
        holder.description.text = post.body
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

    fun addPosts(posts: List<Post>) {
        postsList.clear()
        postsList.addAll(posts)
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val description: TextView

        init {
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.body)
        }
    }
}