package com.mager.gamer.ui.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.databinding.FragmentHomeBinding
import com.mager.gamer.ui.postingan.BuatPostinganActivity
import com.mager.gamer.ui.postingan.DetailPostinganActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel : HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        lifecycleScope.launch {
            viewModel.getAllPost()
        }
    }

    private fun setupObserver() {
        viewModel.postinganResult.observe(viewLifecycleOwner) {
            binding.recyclerPostingan.apply {
                adapter = PostinganAdapter(it.toMutableList(), onDetailClick = {
                    startActivity(Intent(requireContext(), DetailPostinganActivity::class.java))
                }, onCopyClick = {
                    val clipboard: ClipboardManager = (requireActivity()).getSystemService(Context.CLIPBOARD_SERVICE)
                            as ClipboardManager
                    val clip = ClipData.newPlainText("link berhasil disalin", it)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(requireContext(), "Link berhasil disalin", Toast.LENGTH_LONG).show()
                })
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            }
            binding.cardPost.setOnClickListener {
                val intent = Intent(activity, BuatPostinganActivity::class.java)
                startActivity(intent)
            }

            binding.cardUpload.setOnClickListener {
                val intent = Intent(activity, BuatPostinganActivity::class.java)
                intent.putExtra(BuatPostinganActivity.INTENT_IMAGE_MODE, true)
                startActivity(intent)
            }
            binding.cardLive.setOnClickListener {
                val intent = Intent(activity, BuatPostinganActivity::class.java)
                intent.putExtra(BuatPostinganActivity.INTENT_LIVE_MODE, true)
                startActivity(intent)
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}