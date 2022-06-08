package com.mager.gamer.ui.user.follow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.user.Content
import com.mager.gamer.databinding.ItemUserBinding

class FollowerAdapter(
    var follower: MutableList<Content>,
    private val onDetailClick: (Content, Int) -> Unit
) : RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            follower: List<Content>,
            onDetailClick: (Content, Int) -> Unit
        ) {
            val userFollower = follower[absoluteAdapterPosition]
            binding.txtName.text = userFollower.userFollower.nama
            binding.txtUsername.text = userFollower.userFollower.username
            Glide.with(binding.imgUser.context)
                .load(userFollower.userFollower.fotoProfile)
                .error(R.drawable.logo_mager_1)
                .into(binding.imgUser)
            binding.itemUser.setOnClickListener {
                onDetailClick(userFollower, absoluteAdapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(follower, onDetailClick)
    }

    override fun getItemCount() = follower.size
}