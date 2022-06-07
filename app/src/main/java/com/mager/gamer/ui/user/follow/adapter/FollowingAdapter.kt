package com.mager.gamer.ui.user.follow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.user.getfollowing.Content
import com.mager.gamer.databinding.ItemUserBinding

class FollowingAdapter(
    var following: MutableList<Content>,
    private val onDetailClick: (Content, Int) -> Unit
) : RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            following: List<Content>,
            onDetailClick: (Content, Int) -> Unit
        ) {
            val userFollowing = following[absoluteAdapterPosition]
            Glide.with(binding.imgUser)
                .load(userFollowing.userFollowing.fotoProfile)
                .error(R.drawable.logo_mager_1)
                .into(binding.imgUser)
            binding.txtName.text = userFollowing.userFollowing.nama
            binding.txtUsername.text = userFollowing.userFollowing.username
            binding.itemUser.setOnClickListener {
                onDetailClick(userFollowing, absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(following, onDetailClick)
    }

    override fun getItemCount() = following.size
}