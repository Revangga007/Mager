package com.mager.gamer.ui.komunitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.R
import com.mager.gamer.databinding.FragmentKomunitasBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KomunitasFragment : Fragment() {

    private val viewModel: KomunitasViewModel by activityViewModels()
    private var _binding: FragmentKomunitasBinding? = null
    private val binding get() = _binding!!
    private val komunitasAdapter = RekomendasiKomunitasAdapter(mutableListOf(), onDetailClick = {


    }, onJoinClick = {

    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKomunitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBuatKomunitas.backgroundTintList = null
        binding.btnBuatKomunitas.setBackgroundResource(R.drawable.gradient_blue_color2)
//        binding.recyclerKomunitasmu.apply {
//            layoutManager = LinearLayoutManager(
//                context, RecyclerView.VERTICAL, false
//            )
//            adapter = KomunitasmuAdapter
//        }
        binding.recyclerRekomendasiKomunitas.apply {
            layoutManager = LinearLayoutManager(
                context, RecyclerView.VERTICAL, false
            )
            adapter = komunitasAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}