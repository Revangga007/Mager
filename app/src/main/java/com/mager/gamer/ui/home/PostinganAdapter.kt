package com.mager.gamer.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.get.Data

import com.mager.gamer.databinding.ItemPostinganBinding
import com.mager.gamer.databinding.ItemPostinganGambarBinding
import com.mager.gamer.databinding.ItemPostinganLinkBinding
import com.mager.gamer.databinding.ItemPostinganVideoBinding

class PostinganAdapter(
    var postingan: MutableList<Data>,
    private val onDetailClick: (Data, Int) -> Unit,
    private val onCopyClick: (String) -> Unit,
    private val onVideoClick: (Data) -> Unit,
    private val onLikeClick: (Data) -> Unit,

) : RecyclerView.Adapter<PostinganRecyclerViewHolder>() {

    companion object {
        const val POSTINGAN_TEXT = 1
        const val POSTINGAN_IMAGE = 2
        const val POSTINGAN_VIDEO = 3
        const val POSTINGAN_LIVESTREAM = 4
    }

    private val idUser= MagerSharedPref.userId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostinganRecyclerViewHolder {
        return when (viewType) {
            POSTINGAN_TEXT -> PostinganRecyclerViewHolder.PostinganTextViewHolder(
                ItemPostinganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            POSTINGAN_IMAGE  -> PostinganRecyclerViewHolder.PostinganImageViewHolder(
                ItemPostinganGambarBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            POSTINGAN_LIVESTREAM -> PostinganRecyclerViewHolder.PostinganLinkViewHolder(
                ItemPostinganLinkBinding.inflate(
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
                idUser,
                post,
                onDetailClick,
                onCopyClick,
                onLikeClick,
            )
            is PostinganRecyclerViewHolder.PostinganImageViewHolder -> holder.bind(
                idUser,
                post,
                onDetailClick,
                onCopyClick,
                onLikeClick,

            )
            is PostinganRecyclerViewHolder.PostinganVideoViewHolder -> holder.bind(
                idUser,
                post,
                onDetailClick,
                onCopyClick,
                onVideoClick,
                onLikeClick,
            )
            is PostinganRecyclerViewHolder.PostinganLinkViewHolder -> holder.bind(
                idUser,
                post,
                onDetailClick,
                onCopyClick,
                onLikeClick,
            )

        }
    }

    override fun getItemCount() = postingan.size

    override fun getItemViewType(position: Int): Int {
        val post = postingan[position]
        return when (post.tipePost) {
            "foto" -> POSTINGAN_IMAGE
            "video" -> POSTINGAN_VIDEO
            "livestream" -> POSTINGAN_LIVESTREAM
            else -> POSTINGAN_TEXT
        }
    }
}