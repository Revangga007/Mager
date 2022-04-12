package com.mager.gamer.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.databinding.ItemPostinganBinding
import com.mager.gamer.databinding.ItemPostinganGambarBinding
import com.mager.gamer.databinding.ItemPostinganVideoBinding

class PostinganAdapter(
    var postingan: MutableList<Data>,
    private val onDetailClick: (Data) -> Unit,
    private val onCopyClick: (String) -> Unit
) : RecyclerView.Adapter<PostinganRecyclerViewHolder>() {

    companion object {
        const val POSTINGAN_TEXT = 1
        const val POSTINGAN_IMAGE = 2
        const val POSTINGAN_VIDEO = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostinganRecyclerViewHolder {
        return when (viewType) {
            POSTINGAN_TEXT -> PostinganRecyclerViewHolder.PostinganTextViewHolder(
                ItemPostinganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            POSTINGAN_IMAGE -> PostinganRecyclerViewHolder.PostinganImageViewHolder(
                ItemPostinganGambarBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> PostinganRecyclerViewHolder.PostinganVideoViewHolder(
                ItemPostinganVideoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: PostinganRecyclerViewHolder, position: Int) {
        val post = postingan[position]
        when (holder) {
            is PostinganRecyclerViewHolder.PostinganTextViewHolder -> holder.bind(
                post,
                onDetailClick,
                onCopyClick
            )
            is PostinganRecyclerViewHolder.PostinganImageViewHolder -> holder.bind(
                post,
                onDetailClick,
                onCopyClick
            )
            is PostinganRecyclerViewHolder.PostinganVideoViewHolder -> holder.bind(
                post,
                onDetailClick,
                onCopyClick
            )

        }
    }

    override fun getItemCount() = postingan.size

    override fun getItemViewType(position: Int): Int {
        val post = postingan[position]
        val ext = post.files?.split(".")?.last()
        val isImage = (ext == "png" || ext == "jpg" || ext == "jpeg")
        val isVideo = (ext == "mp4")
        return when {
            isImage -> POSTINGAN_IMAGE
            isVideo -> POSTINGAN_VIDEO
            else -> POSTINGAN_TEXT
        }
    }
}