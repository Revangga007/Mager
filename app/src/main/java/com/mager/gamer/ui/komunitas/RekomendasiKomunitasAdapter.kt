package com.mager.gamer.ui.komunitas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.komunitas.get.Content
import com.mager.gamer.databinding.ItemKomunitasBinding

class RekomendasiKomunitasAdapter(
    var rekomendasi: MutableList<Content>,
    private val onDetailClick : (Content) -> Unit,
    private val onJoinClick : (Content) -> Unit
) : RecyclerView.Adapter<RekomendasiKomunitasAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemKomunitasBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RekomendasiKomunitasAdapter.ViewHolder {
        val view = ItemKomunitasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RekomendasiKomunitasAdapter.ViewHolder, position: Int) {
        val komunitas = rekomendasi[position]
        holder.binding.txtJudul.text = komunitas.namaKomunitas
        holder.binding.txtMember.text = komunitas.jumlahAnggota.toString()
        holder.binding.txtLocation.text = komunitas.lokasi
        Glide.with(holder.binding.imgKomunitas.context)
            .load(komunitas.banner)
            .error(R.drawable.logo_mager_1)
            .into(holder.binding.imgKomunitas)
    }

    override fun getItemCount() = rekomendasi.size

}