package com.mager.gamer.ui.postingan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.postingan.get.KomentarBy
import com.mager.gamer.databinding.ItemKomenBinding

class KomentarAdapter(
    var comments: MutableList<KomentarBy>,
    var onLongClick: (KomentarBy) -> Unit
): RecyclerView.Adapter<KomentarAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemKomenBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KomentarAdapter.ViewHolder {
        val view = ItemKomenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: KomentarAdapter.ViewHolder, position: Int) {
        val komen = comments[position]
        holder.binding.txtIsiKomen.text = komen.isiKomentar
        holder.binding.txtNamaKomentator.text = komen.user.nama
        Glide.with(holder.itemView)
            .load(komen.user.fotoProfile)
            .error(R.drawable.logo_mager_1)
            .into(holder.binding.imgFoto)
        holder.binding.txtNamaKomentator.setOnClickListener {

        }
        holder.binding.root.setOnLongClickListener {
            onLongClick(komen)
            true
        }
    }

    override fun getItemCount() = comments.size
}