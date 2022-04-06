package com.mager.gamer.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.data.model.remote.postingan.Data
import com.mager.gamer.data.model.remote.postingan.PostinganResponse
import com.mager.gamer.databinding.ItemPostinganBinding

class PostinganAdapter (
    var postingan : MutableList<Data>,
    private val onDetailClick: (Data) -> Unit
    ) : RecyclerView.Adapter<PostinganAdapter.ViewHolder>(){

    inner  class  ViewHolder(val binding: ItemPostinganBinding) :
    RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostinganAdapter.ViewHolder {
        val view = ItemPostinganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostinganAdapter.ViewHolder, position: Int) {
        val post = postingan[position]
        holder.binding.itemPosting.setOnClickListener {
            onDetailClick(post)
        }
    }

    override fun getItemCount() = postingan.size
}