package com.mager.gamer.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.user.detail.Data
import com.mager.gamer.databinding.FragmentUserBinding
import com.mager.gamer.databinding.LoadingDialogBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.home.PostinganAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UserFragment : Fragment() {

    private val viewModel: UserViewModel by activityViewModels()
    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var loading: CustomLoadingDialog
    private val postUserAdapter = PostinganAdapter(mutableListOf(),
        onDetailClick = { data, pos -> },
        onCopyClick = {},
        onVideoClick = {}
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
        loading = CustomLoadingDialog(requireContext())

        lifecycleScope.launch {
            viewModel.getAllfollower
            viewModel.getALlFollowing
        }

        binding.btnSetting.setOnClickListener {
        }


        setupObserver()
    }

    private fun setupObserver() {
        viewModel.userDetail.observe(viewLifecycleOwner) {
            if (it.status == "200") {
                Glide.with(binding.imgPhoto.context)
                    .load(it.data.fotoProfile)
                    .error(R.drawable.logo_mager_1)
                    .into(binding.imgPhoto)
                binding.txtName.
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}