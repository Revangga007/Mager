package com.mager.gamer.ui.komunitas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.komunitas.get.Content
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
        if (it.resultCode == Activity.RESULT_OK) moveItemCommunity()
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
        }
    }

    private fun moveItemCommunity() {
        if (targetCommunityPosition > -1) {
            val target =
                communityAdapter.community[targetCommunityPosition].copy(acceptance = true)
            joinedCommunityAdapter.community.add(target)
            joinedCommunityAdapter.notifyItemInserted(joinedCommunityAdapter.community.size - 1)
            communityAdapter.community.removeAt(targetCommunityPosition)
            communityAdapter.notifyItemRemoved(targetCommunityPosition)
        }
    }

    private fun intentToDetail(content: Content, pos: Int) {
        targetCommunityPosition = pos
        val i = Intent(requireContext(), DetailCommunityActivity::class.java)
        i.putExtra("data", content)
        intentToDetailWithResult.launch(i)
    }



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
            val lastPos = communityAdapter.community.size - 1
            communityAdapter.community.clear()
            communityAdapter.notifyItemRangeRemoved(0, lastPos)
            communityAdapter.community.addAll(it)
            communityAdapter.notifyItemRangeInserted(0, it.size - 1)
        }
        viewModel.joinResponse.observe(viewLifecycleOwner) {
            moveItemCommunity()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


