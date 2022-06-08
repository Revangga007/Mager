package com.mager.gamer.ui.user.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.komunitas.get.Komunitas
import com.mager.gamer.data.model.remote.user.Content
import com.mager.gamer.data.model.remote.user.detail.Data
import com.mager.gamer.data.model.remote.user.follow.FollowResponse
import com.mager.gamer.databinding.ActivityDetailCommunityBinding
import com.mager.gamer.databinding.ActivityFollowingBinding
import com.mager.gamer.databinding.ActivityProfileBinding
import com.mager.gamer.ui.home.PostinganAdapter
import com.mager.gamer.ui.user.UserViewModel
import com.mager.gamer.ui.user.follow.FollowerActivity
import com.mager.gamer.ui.user.follow.FollowingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity: AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityProfileBinding
    private lateinit var userData: Content
    private var donefollow = false
    private lateinit var isfollow: com.mager.gamer.data.model.remote.user.follow.Data
    private val postUserAdapter = PostinganAdapter(mutableListOf(),
        onDetailClick = { data, pos -> },
        onCopyClick = {},
        onVideoClick = {},
        onLikeClick = {},
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableExtra<Content?>("data")?.let {
            userData = it
            lifecycleScope.launch {
                viewModel.getAllFollowers(it.id)
                viewModel.getAllFollowing(it.id)
                viewModel.getUserDetail(it.id)
                viewModel.getAllPost(it.id)
            }
        }

        binding.recyclerPostingan.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postUserAdapter
        }
        binding.btnIkuti.setOnClickListener {
            lifecycleScope.launch {
                viewModel.followUser(isfollow.userFollower.id,isfollow.userFollowing.id)
            }
        }
        setupObserver()
    }
    private fun setupObserver(){
        viewModel.userDetail.observe(this) {
            if (it.status == "200") {
                Glide.with(binding.imgPhoto.context)
                    .load(it.data.fotoProfile)
                    .error(R.drawable.logo_mager_1)
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
                binding.recyclerPostingan.apply {
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                }
            }
        }
        viewModel.followingResponse.observe(this) {
            binding.txtNumbFollowing.text = it.data.content.size.toString()
            binding.linearFollowing.setOnClickListener {
                val i = Intent(this, FollowingActivity::class.java)
                startActivity(i)
            }
        }
        viewModel.followerResponse.observe(this) {
            binding.txtNumbFollower.text = it.data.content.size.toString()
            binding.linearFollower.setOnClickListener {
                val i = Intent(this, FollowerActivity::class.java)
                startActivity(i)
            }
        }
        viewModel.postinganResponse.observe(this) {
            val lastPos = postUserAdapter.postingan.size - 1
            postUserAdapter.postingan.clear()
            postUserAdapter.notifyItemRangeRemoved(0, lastPos)
            postUserAdapter.postingan.addAll(it.data)
            postUserAdapter.notifyItemRangeInserted(0, it.data.size - 1)
        }
        viewModel.followResponse.observe(this){
            donefollow = !donefollow
            if (donefollow) {
                Toast.makeText(this, "Diikuti", Toast.LENGTH_SHORT).show()
                binding.btnIkuti.visibility = View.GONE
                binding.btnMengikuti.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Batal Mengikuti", Toast.LENGTH_SHORT).show()
                binding.btnIkuti.visibility = View.VISIBLE
                binding.btnMengikuti.visibility = View.GONE
            }
            setResult(RESULT_OK)
        }
    }

}