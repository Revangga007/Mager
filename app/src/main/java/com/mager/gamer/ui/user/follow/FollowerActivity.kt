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
import com.mager.gamer.databinding.ActivityFollowBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.user.follow.adapter.FollowerAdapter
import com.mager.gamer.ui.user.profile.ProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowerActivity: AppCompatActivity() {

    private lateinit var binding: ActivityFollowBinding
    private val viewModel: FollowerViewModel by viewModels()
    private var targetPosition = -1
    private var adapterFoll = FollowerAdapter(mutableListOf(),
    onDetailClick = {data, pos -> })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFollowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userId = intent.getIntExtra("userId", -1)
        lifecycleScope.launch {
            if (userId == -1) {
                viewModel.getAllFollower(MagerSharedPref.userId!!)
            } else {
                viewModel.getAllFollower(userId)
            }
        }

        binding.imgLeft.setOnClickListener {
            finish()
        }

        setupObserver()
    }
//    private fun intentToDetail(content: Content, pos: Int) {
//        targetPosition = pos
//        val i = Intent(this, ProfileActivity::class.java)
//        i.putExtra("data", content)
//        startActivity(i)
//    }
    private fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.followerResponse.observe(this) {
            adapterFoll.follower.clear()
            adapterFoll.follower.addAll(it.data.content)
            binding.rvFollower.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = adapterFoll
            }
        }
    }
}