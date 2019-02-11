package com.sumitanantwar.postsbrowser.mobile.ui.activities.postslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sumitanantwar.postsbrowser.data.model.Post
import com.sumitanantwar.postsbrowser.mobile.R
import javax.inject.Inject

class PostListAdapter @Inject constructor() : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    private var posts: List<Post> = arrayListOf()

    //======= Adapter Lifecycle =======
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cellView = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_cell, parent, false)
        return ViewHolder(cellView)
    }

    override fun getItemCount(): Int {
        return posts.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.postId.text  = post.id.toString()
        holder.title.text   = post.title
        holder.userId.text  = post.userId.toString()
    }


    //======= Public =======
    fun updatePosts(posts: List<Post>) {
        this.posts = posts
        notifyDataSetChanged()
    }


    //======= ViewHolder =======
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var postId: TextView
        var title: TextView
        var userId: TextView

        init {
            postId  = view.findViewById(R.id.text_post_id)
            title   = view.findViewById(R.id.text_post_title)
            userId  = view.findViewById(R.id.text_userid)
        }
    }
}