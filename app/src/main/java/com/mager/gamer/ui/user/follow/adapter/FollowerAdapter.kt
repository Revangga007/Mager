package com.mager.gamer.ui.user.follow.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.user.getfollowers.Content
import com.mager.gamer.databinding.ItemUserBinding

class FollowerAdapter(
    var follower: MutableList<Content>
): RecyclerView.Adapter<FollowerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userFollower = follower[position]
        Glide.with(holder.itemView)
            .load(userFollower.userFollower.fotoProfile)
            .error(R.drawable.logo_mager_1)
            .into(holder.binding.imgUser)
        holder.binding.txtName.text = userFollower.userFollower.nama
        holder.binding.txtUsername.text = userFollower.userFollower.username
    }

    override fun getItemCount() = follower.size
}