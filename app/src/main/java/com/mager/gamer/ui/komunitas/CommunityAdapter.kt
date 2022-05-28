package com.mager.gamer.ui.komunitas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.komunitas.get.Content
import com.mager.gamer.databinding.ItemKomunitasBinding

class CommunityAdapter(
    var community: MutableList<Content>,
    private val onDetailClick: (Content, Int) -> Unit,
    private val onJoinClick: (Content, Int) -> Unit
) : RecyclerView.Adapter<CommunityAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemKomunitasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            community: List<Content>,
            onDetailClick: (Content, Int) -> Unit,
            onJoinClick: (Content, Int) -> Unit
        ) {
            val com = community[absoluteAdapterPosition]
            binding.txtName.text = com.namaKomunitas
            binding.txtMember.text = com.jumlahAnggota.toString()
            binding.txtLocation.text = com.lokasi
            binding.btnJoin.visibility = if (com.acceptance) View.GONE else View.VISIBLE

            Glide.with(binding.imgPhoto.context)
                .load(com.banner)
                .error(R.drawable.logo_mager_1)
                .into(binding.imgPhoto)
            binding.btnJoin.setOnClickListener { onJoinClick(com, absoluteAdapterPosition) }
            binding.itemCommunity.setOnClickListener {
                onDetailClick(com, absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommunityAdapter.ViewHolder {
        val view = ItemKomunitasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityAdapter.ViewHolder, position: Int) {
        holder.bind(community, onDetailClick, onJoinClick)
    }

    override fun getItemCount() = community.size

}