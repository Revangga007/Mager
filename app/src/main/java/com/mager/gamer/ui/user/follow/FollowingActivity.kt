package com.mager.gamer.ui.user.follow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.user.Content
import com.mager.gamer.databinding.ActivityFollowingBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.user.follow.adapter.FollowingAdapter
import com.mager.gamer.ui.user.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowingActivity : AppCompatActivity() {

    private val viewModel: FollowingViewModel by viewModels()
    private lateinit var binding: ActivityFollowingBinding
    private var adapterFoll = FollowingAdapter(
        following = mutableListOf(),
        onDetailClick = {data, pos -> })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFollowingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgLeft.setOnClickListener {
            finish()
        }
        lifecycleScope.launch {
            viewModel.getAllFollowing(MagerSharedPref.userId!!)
        }

        setupObserver()
    }

    private fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.followingResponse.observe(this) {
            adapterFoll.following.clear()
            adapterFoll.following.addAll(it.data.content)
            binding.rvFollowing.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = adapterFoll
            }
        }
    }

}