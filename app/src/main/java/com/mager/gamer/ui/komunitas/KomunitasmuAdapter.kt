package com.mager.gamer.ui.komunitas

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.data.model.remote.komunitas.get.Data
import com.mager.gamer.databinding.ItemKomunitasBinding
import com.mager.gamer.ui.home.PostinganRecyclerViewHolder

class KomunitasmuAdapter(
    var komunitas: MutableList<Data>,
    private val onDetailClick: (Data) -> Unit,
) : RecyclerView.Adapter<KomunitasmuAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemKomunitasBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KomunitasmuAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: KomunitasmuAdapter.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}