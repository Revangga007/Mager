package com.mager.gamer.ui.komunitas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mager.gamer.databinding.FragmentHomeBinding
import com.mager.gamer.databinding.FragmentKomunitasBinding
import com.mager.gamer.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KomunitasFragment : Fragment() {

    private val viewModel : KomunitasViewModel by activityViewModels()
    private var _binding: FragmentKomunitasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKomunitasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}