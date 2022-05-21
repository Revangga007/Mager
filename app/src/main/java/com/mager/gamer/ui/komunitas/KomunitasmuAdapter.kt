package com.mager.gamer.ui.komunitas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.data.model.remote.komunitas.get.Content
import com.mager.gamer.data.model.remote.komunitas.get.Data
import com.mager.gamer.databinding.ItemKomenBinding
import com.mager.gamer.databinding.ItemKomunitasBinding
import com.mager.gamer.ui.home.PostinganRecyclerViewHolder

class KomunitasmuAdapter(
    var komunitas: List<Content>,
    private val onDetailClick: (Data) -> Unit,
) : RecyclerView.Adapter<KomunitasmuAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemKomunitasBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KomunitasmuAdapter.ViewHolder {
        val kom = ItemKomunitasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(kom)
    }

    override fun onBindViewHolder(holder: KomunitasmuAdapter.ViewHolder, position: Int) {
        val komunitasmu = komunitas[position]
        holder.binding.txtJudul.text = komunitasmu.namaKomunitas
        holder.binding.txtMember.text = komunitasmu.jumlahAnggota.toString()
        holder.binding.txtLocation.text = komunitasmu.lokasi

    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}