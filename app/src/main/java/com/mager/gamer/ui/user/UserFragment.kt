package com.mager.gamer.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.user.detail.Data
import com.mager.gamer.databinding.FragmentUserBinding
import com.mager.gamer.ui.home.PostinganAdapter
import com.mager.gamer.ui.postingan.DetailPostinganActivity
import com.mager.gamer.ui.user.follow.FollowerActivity
import com.mager.gamer.ui.user.follow.FollowingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private var dataUser: Data? = null
    private val postUserAdapter = PostinganAdapter(mutableListOf(),
        onDetailClick = { data, pos -> },
        onCopyClick = {},
        onVideoClick = {},
        onLikeClick = {},
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerPostingan.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postUserAdapter
        }
        lifecycleScope.launch {
            viewModel.getUserDetail(MagerSharedPref.userId!!)
            viewModel.getAllFollowers(MagerSharedPref.userId!!)
            viewModel.getAllFollowing(MagerSharedPref.userId!!)
            viewModel.getAllPost(MagerSharedPref.userId!!)
        }
        binding.btnSetting.setOnClickListener {
            val i = Intent(requireContext(), UserSettingActivity::class.java)
            i.putExtra("data", dataUser)
            startActivity(i)
        }
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.userDetail.observe(viewLifecycleOwner) {
            if (it.status == "200") {
                Glide.with(binding.imgPhoto.context)
                    .load(it.data.fotoProfile)
                    .error(R.drawable.logo_mager_3)
                    .into(binding.imgPhoto)

                binding.txtName.text = it.data.nama
                binding.txtUser.text = it.data.username
                binding.txtBio.text = it.data.biodata
                binding.txtLoc.text = it.data.lokasi
                if (it.data.gender.equals("Perempuan", true)) {
                    binding.icGender.setImageResource(R.drawable.ic_girl)
                } else {
                    binding.icGender.setImageResource(R.drawable.ic_boy2)
                }
            }
        }
        viewModel.followingResponse.observe(viewLifecycleOwner) {
            binding.txtNumbFollowing.text = it.data.content.size.toString()
            binding.linearFollowing.setOnClickListener {
                val i = Intent(requireContext(), FollowingActivity::class.java)
                startActivity(i)
            }
        }
        viewModel.followerResponse.observe(viewLifecycleOwner) {
            binding.txtNumbFollower.text = it.data.content.size.toString()
            binding.linearFollower.setOnClickListener {
                val i = Intent(requireContext(), FollowerActivity::class.java)
                startActivity(i)
            }
        }
        viewModel.postinganResponse.observe(viewLifecycleOwner) {
            postUserAdapter.postingan.clear()
            postUserAdapter.postingan.addAll(it.data)
            postUserAdapter.notifyDataSetChanged()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}