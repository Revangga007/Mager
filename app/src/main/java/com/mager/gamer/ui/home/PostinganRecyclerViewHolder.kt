package com.mager.gamer.ui.home

import android.content.Intent
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.commit.app.ui.homepage.VideoActivity
import com.mager.gamer.R
import com.mager.gamer.TimeAgo.toTimeAgo
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.databinding.ItemPostinganBinding
import com.mager.gamer.databinding.ItemPostinganGambarBinding
import com.mager.gamer.databinding.ItemPostinganLinkBinding
import com.mager.gamer.databinding.ItemPostinganVideoBinding
import com.mager.gamer.ui.postingan.KomentarAdapter

sealed class PostinganRecyclerViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {


    class PostinganTextViewHolder(private val binding: ItemPostinganBinding) :
        PostinganRecyclerViewHolder(binding) {
        fun bind(
            idUser: Int?,
            postingan: Data,
            onDetailClick: (Data, Int) -> Unit,
            onCopyClick: (String) -> Unit
        ) {
            if (idUser != null) {
                val find = postingan.likedBy.find { like ->
                    like.user.id == idUser
                }
                binding.icLike.setImageResource(
                    if (find != null) R.drawable.ic_liked
                    else R.drawable.ic_like_outline
                )
            }
            binding.txtPosting.text = postingan.postText
            binding.txtJmlSuka.text = postingan.jumlahLike.toString()
            binding.txtJmlKomen.text = postingan.jumlahKomentar.toString()
            binding.txtNama.text = postingan.createdBy.nama
            binding.txtUsername.text = postingan.createdBy.username
            binding.txtWaktu.text = postingan.createdDate.toTimeAgo()
            if (postingan.jumlahKomentar == 0) {
                binding.recyclerComment.visibility = View.GONE
                binding.txtSemuaKomen.visibility = View.GONE
            } else {
                binding.recyclerComment.apply {
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                    adapter = KomentarAdapter(postingan.komentarBy.toMutableList()) {
                    }
                }
                binding.txtSemuaKomen.visibility = View.VISIBLE
            }

            binding.btnCopy.setOnClickListener {
                onCopyClick(postingan.linkPostingan ?: "")
            }
            binding.itemPosting.setOnClickListener {
                onDetailClick(postingan, absoluteAdapterPosition)
            }

        }

    }

    class PostinganImageViewHolder(private val binding: ItemPostinganGambarBinding) :
        PostinganRecyclerViewHolder(binding) {
        fun bind(
            idUser: Int?,
            postingan: Data,
            onDetailClick: (Data, Int) -> Unit,
            onCopyClick: (String) -> Unit
        ) {
            if (idUser != null) {
                val find = postingan.likedBy.find { like ->
                    like.user.id == idUser
                }
                binding.icLike.setImageResource(
                    if (find != null) R.drawable.ic_liked
                    else R.drawable.ic_like_outline
                )
            }

            binding.txtPosting.text = postingan.postText
            binding.txtJmlSuka.text = postingan.jumlahLike.toString()
            binding.txtJmlKomen.text = postingan.jumlahKomentar.toString()
            binding.txtNama.text = postingan.createdBy.nama
            binding.txtUsername.text = postingan.createdBy.username
            binding.txtWaktu.text = postingan.createdDate.toTimeAgo()
            if (postingan.jumlahKomentar == 0) {
                binding.recyclerComment.visibility = View.GONE
                binding.txtSemuaKomen.visibility = View.GONE
            } else {
                binding.recyclerComment.apply {
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                    adapter = KomentarAdapter(postingan.komentarBy.toMutableList()) {
                    }
                }
                binding.txtSemuaKomen.visibility = View.VISIBLE
            }

            binding.btnCopy.setOnClickListener {
                onCopyClick(postingan.linkPostingan ?: "")
            }
            binding.itemPosting.setOnClickListener {
                onDetailClick(postingan, absoluteAdapterPosition)
            }

            if (postingan.files != null) {
                Glide.with(binding.imgPosting.context)
                    .load(postingan.files)
                    .error(R.drawable.logo_mager_1)
                    .into(binding.imgPosting)
            } else if (postingan.linkLivestream != null) {
                Glide.with(binding.imgPosting.context)
                    .load(postingan.linkLivestream)
                    .error(R.drawable.logo_mager_1)
                    .into(binding.imgPosting)
            }
        }
    }

    class PostinganVideoViewHolder(private val binding: ItemPostinganVideoBinding) :
        PostinganRecyclerViewHolder(binding) {
        fun bind(
            idUser: Int?,
            postingan: Data,
            onDetailClick: (Data, Int) -> Unit,
            onCopyClick: (String) -> Unit,

        ) {
            if (idUser != null) {
                val find = postingan.likedBy.find { like ->
                    like.user.id == idUser
                }
                binding.icLike.setImageResource(
                    if (find != null) R.drawable.ic_liked
                    else R.drawable.ic_like_outline
                )
            }

            binding.txtPosting.text = postingan.postText
            binding.txtJmlSuka.text = postingan.jumlahLike.toString()
            binding.txtJmlKomen.text = postingan.jumlahKomentar.toString()
            binding.txtNama.text = postingan.createdBy.nama
            binding.txtUsername.text = postingan.createdBy.username
            binding.txtWaktu.text = postingan.createdDate.toTimeAgo()
            if (postingan.jumlahKomentar == 0) {
                binding.recyclerComment.visibility = View.GONE
                binding.txtSemuaKomen.visibility = View.GONE
            } else {
                binding.recyclerComment.apply {
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                    adapter = KomentarAdapter(postingan.komentarBy.toMutableList()) {
                    }
                }
                binding.txtSemuaKomen.visibility = View.VISIBLE
            }
            binding.btnCopy.setOnClickListener {
                onCopyClick(postingan.linkPostingan ?: "")
            }
            binding.itemPosting.setOnClickListener {
                onDetailClick(postingan, absoluteAdapterPosition)
            }
            binding.imgPreview.setOnClickListener {
                val i = Intent(requireContext(), VideoActivity::class.java)
                i.putExtra(VideoActivity.INTENT_VIDEO_URL, .[0].url)
                startActivity(i)
            }
            if (postingan.files != null) {
                Glide.with(binding.imgPreview.context)
                    .load(postingan.files)
                    .error(R.drawable.logo_mager_1)
                    .into(binding.imgPreview)
            } else if (postingan.linkLivestream != null) {
                Glide.with(binding.cardVideoPosting.context)
                    .load(postingan.linkLivestream)
                    .error(R.drawable.logo_mager_1)
                    .into(binding.imgPreview)
            }
        }
    }
    class PostinganLinkViewHolder(private val binding: ItemPostinganLinkBinding) :
        PostinganRecyclerViewHolder(binding) {
        fun bind(
            idUser: Int?,
            postingan: Data,
            onDetailClick: (Data, Int) -> Unit,
            onCopyClick: (String) -> Unit
        ) {
            if (idUser != null) {
                val find = postingan.likedBy.find { like ->
                    like.user.id == idUser
                }
                binding.icLike.setImageResource(
                    if (find != null) R.drawable.ic_liked
                    else R.drawable.ic_like_outline
                )
            }

            binding.txtPosting.text = postingan.postText
            binding.txtLink.text = postingan.linkLivestream
            binding.txtJmlSuka.text = postingan.jumlahLike.toString()
            binding.txtJmlKomen.text = postingan.jumlahKomentar.toString()
            binding.txtNama.text = postingan.createdBy.nama
            binding.txtUsername.text = postingan.createdBy.username
            binding.txtWaktu.text = postingan.createdDate.toTimeAgo()
            if (postingan.jumlahKomentar == 0) {
                binding.recyclerComment.visibility = View.GONE
                binding.txtSemuaKomen.visibility = View.GONE
            } else {
                binding.recyclerComment.apply {
                    visibility = View.VISIBLE
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                    adapter = KomentarAdapter(postingan.komentarBy.toMutableList()) {
                    }
                }
                binding.txtSemuaKomen.visibility = View.VISIBLE
            }

            binding.btnCopy.setOnClickListener {
                onCopyClick(postingan.linkPostingan ?: "")
            }
            binding.itemPosting.setOnClickListener {
                onDetailClick(postingan, absoluteAdapterPosition)
            }
        }

    }

}


