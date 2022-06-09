package com.mager.gamer.ui.komunitas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.komunitas.get.Komunitas
import com.mager.gamer.databinding.FragmentKomunitasBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class KomunitasFragment : Fragment() {

    private val viewModel: KomunitasViewModel by viewModels()
    private var _binding: FragmentKomunitasBinding? = null
    private val binding get() = _binding!!
    private var targetCommunityPosition = -1
    private val joinedCommunityAdapter = CommunityAdapter(
        community = mutableListOf(),
        onDetailClick = { data, pos -> intentToDetail(data, pos) },
        onJoinClick = { _, _ -> }
    )
    private val communityAdapter = CommunityAdapter(
        community = mutableListOf(),
        onDetailClick = { data, pos -> intentToDetail(data, pos) },
        onJoinClick = { data, pos ->
            targetCommunityPosition = pos
            lifecycleScope.launch {
                viewModel.joinCommunity(data.id)
            }
        }
    )

    private val intentToDetailWithResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            lifecycleScope.launch {
                viewModel.getAllCommunity()
                viewModel.getJoinedCommunity()
            }
        }
    }

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

        binding.recyclerRekomendasiKomunitas.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = communityAdapter
        }
        binding.recyclerKomunitasmu.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = joinedCommunityAdapter
        }
        setupObserver()

        lifecycleScope.launch {
            viewModel.getAllCommunity()
            viewModel.getJoinedCommunity()
        }
    }

    private fun intentToDetail(content: Komunitas, pos: Int) {
        targetCommunityPosition = pos
        val i = Intent(requireContext(), DetailCommunityActivity::class.java)
        i.putExtra("data", content)
        intentToDetailWithResult.launch(i)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserver() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding.recyclerRekomendasiKomunitas.visibility = View.INVISIBLE
                binding.recyclerKomunitasmu.visibility = View.INVISIBLE
                binding.loading.visibility = View.VISIBLE
                binding.loadingJoined.visibility = View.VISIBLE
            } else {
                binding.recyclerRekomendasiKomunitas.visibility = View.VISIBLE
                binding.recyclerKomunitasmu.visibility = View.VISIBLE
                binding.loading.visibility = View.GONE
                binding.loadingJoined.visibility = View.GONE
            }
        }
        viewModel.allCommunity.observe(viewLifecycleOwner) {
            communityAdapter.community.clear()
            communityAdapter.community.addAll(it)
            communityAdapter.notifyDataSetChanged()
        }
        viewModel.joinedCommunity.observe(viewLifecycleOwner) {
            joinedCommunityAdapter.community.clear()
            joinedCommunityAdapter.community.addAll(it)
            joinedCommunityAdapter.notifyDataSetChanged()
        }
        viewModel.joinResponse.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                viewModel.getAllCommunity()
                viewModel.getJoinedCommunity()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


