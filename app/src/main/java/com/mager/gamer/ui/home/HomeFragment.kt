package com.mager.gamer.ui.home

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.MainActivity
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.get.LikedBy
import com.mager.gamer.databinding.FragmentHomeBinding
import com.mager.gamer.ui.login.LoginActivity
import com.mager.gamer.ui.postingan.BuatPostinganActivity
import com.mager.gamer.ui.postingan.DetailPostinganActivity
import com.mager.gamer.ui.postingan.KomentarAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val intentWithResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                if (lastPositionForUpdate != -1) {
                    val newLike = it.data?.getIntExtra("like", 0)!!
                    val data = it.data?.getParcelableExtra<LikedBy>("data")!!

                    (binding.recyclerPostingan.adapter as PostinganAdapter?)?.let {
                        val post = it.postingan[lastPositionForUpdate]
                        if (post.jumlahLike > newLike) {
                            /** unlike post **/
                            post.likedBy.find { it.user.id == data.user.id }?.let {
                                post.likedBy.remove(it)
                            }
                        } else {
                            /** like post **/
                            post.likedBy.add(data)
                        }
                        it.postingan[lastPositionForUpdate].jumlahLike = newLike
                        it.notifyItemChanged(lastPositionForUpdate)
                    }
                }
            }
        }
    private var lastPositionForUpdate = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.getAllPost()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()

    }

    private fun intentToCreatePost(isImage: Boolean = false, isLive: Boolean = false) {
        val isLogin = MagerSharedPref.isLoggedIn
        if (!isLogin) {
            val i = Intent(requireContext(), LoginActivity::class.java)
            startActivity(i)
            return
        }
        val intent = Intent(requireContext(), BuatPostinganActivity::class.java)
        if (isImage) intent.putExtra(BuatPostinganActivity.INTENT_IMAGE_MODE, true)
        else if (isLive) intent.putExtra(BuatPostinganActivity.INTENT_LIVE_MODE, true)
        startActivity(intent)
    }

    private fun setupObserver() {
        binding.cardPost.setOnClickListener {
            intentToCreatePost()
        }

        binding.cardUpload.setOnClickListener {
            intentToCreatePost(isImage = true)
        }
        binding.cardLive.setOnClickListener {
            intentToCreatePost(isLive = true)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                (requireActivity() as MainActivity).showLoading()
            } else {
                (requireActivity() as MainActivity).hideLoading()
            }
        }
        viewModel.postinganResult.observe(viewLifecycleOwner) {
            binding.recyclerPostingan.apply {
                adapter = PostinganAdapter(it.toMutableList(), onDetailClick = { data, position ->
                    lastPositionForUpdate = position
                    val intent = Intent(requireContext(), DetailPostinganActivity::class.java)
                    intent.putExtra("post", data)
                    intentWithResult.launch(intent)
                }, onCopyClick = {
                    val clipboard: ClipboardManager =
                        (requireActivity()).getSystemService(Context.CLIPBOARD_SERVICE)
                                as ClipboardManager
                    val clip = ClipData.newPlainText("link berhasil disalin", it)
                    clipboard.setPrimaryClip(clip)
                    Toast.makeText(requireContext(), "Link berhasil disalin", Toast.LENGTH_LONG)
                        .show()
                })
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}