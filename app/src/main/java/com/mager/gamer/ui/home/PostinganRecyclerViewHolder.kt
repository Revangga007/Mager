package com.mager.gamer.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.databinding.ItemPostinganBinding
import com.mager.gamer.databinding.ItemPostinganGambarBinding
import com.mager.gamer.databinding.ItemPostinganVideoBinding

sealed class PostinganRecyclerViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    class PostinganTextViewHolder(private val binding: ItemPostinganBinding) :
        PostinganRecyclerViewHolder(binding) {
        fun bind(
            postingan: Data,
            onDetailClick: (Data) -> Unit,
            onCopyClick: (String) -> Unit
        ) {
            binding.txtPosting.text = postingan.postText
            binding.txtPosting.text = postingan.postText
            binding.txtJmlSuka.text = postingan.jumlahLike.toString()
            binding.txtJmlKomen.text = postingan.jumlahKomentar.toString()
            if (postingan.createdBy != null) {
                binding.txtNama.text = postingan.createdBy.nama
                binding.txtUsername.text = postingan.createdBy.username
            }
            binding.txtWaktu.text = postingan.createdDate.toString()
            if (postingan.jumlahKomentar == 0) {
                binding.layoutKomen.visibility = View.GONE
                binding.cardFoto3.visibility = View.GONE
                binding.txtSemuaKomen.visibility = View.GONE
            }

            binding.btnCopy.setOnClickListener {
                onCopyClick(postingan.linkPostingan)
            }
            binding.itemPosting.setOnClickListener {
                onDetailClick(postingan)
            }
        }
    }

    class PostinganImageViewHolder(private val binding: ItemPostinganGambarBinding) :
        PostinganRecyclerViewHolder(binding) {
        fun bind(
            postingan: Data,
            onDetailClick: (Data) -> Unit,
            onCopyClick: (String) -> Unit
        ) {
            binding.txtPosting.text = postingan.postText
            binding.txtPosting.text = postingan.postText
            binding.txtJmlSuka.text = postingan.jumlahLike.toString()
            binding.txtJmlKomen.text = postingan.jumlahKomentar.toString()
            if (postingan.createdBy != null) {
                binding.txtNama.text = postingan.createdBy.nama
                binding.txtUsername.text = postingan.createdBy.username
            }
            binding.txtWaktu.text = postingan.createdDate.toString()
            if (postingan.jumlahKomentar == 0) {
                binding.layoutKomen.visibility = View.GONE
                binding.cardFoto3.visibility = View.GONE
                binding.txtSemuaKomen.visibility = View.GONE
            }

            binding.btnCopy.setOnClickListener {
                onCopyClick(postingan.linkPostingan)
            }
            binding.itemPosting.setOnClickListener {
                onDetailClick(postingan)
            }

//            Glide untuk load gambar
            Glide.with(binding.imgPosting.context)
                .load(postingan.files)
                .error(R.drawable.logo_mager_1)
                .into(binding.imgPosting)
        }
    }

    class PostinganVideoViewHolder(private val binding: ItemPostinganVideoBinding) :
        PostinganRecyclerViewHolder(binding) {
        fun bind(
            postingan: Data,
            onDetailClick: (Data) -> Unit,
            onCopyClick: (String) -> Unit
        ) {
            binding.txtPosting.text = postingan.postText
            binding.txtPosting.text = postingan.postText
            binding.txtJmlSuka.text = postingan.jumlahLike.toString()
            binding.txtJmlKomen.text = postingan.jumlahKomentar.toString()
            if (postingan.createdBy != null) {
                binding.txtNama.text = postingan.createdBy.nama
                binding.txtUsername.text = postingan.createdBy.username
            }
            binding.txtWaktu.text = postingan.createdDate.toString()
            if (postingan.jumlahKomentar == 0) {
                binding.layoutKomen.visibility = View.GONE
                binding.cardFoto3.visibility = View.GONE
                binding.txtSemuaKomen.visibility = View.GONE
            }
            binding.btnCopy.setOnClickListener {
                onCopyClick(postingan.linkPostingan)
            }
            binding.itemPosting.setOnClickListener {
                onDetailClick(postingan)
            }
        }
    }
}