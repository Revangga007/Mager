package com.mager.gamer.ui.user.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.data.model.remote.postingan.get.LikedBy
import com.mager.gamer.data.model.remote.user.Content
import com.mager.gamer.databinding.ActivityProfileBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.home.PostinganAdapter
import com.mager.gamer.ui.postingan.DetailPostinganActivity
import com.mager.gamer.ui.user.UserViewModel
import com.mager.gamer.ui.user.follow.FollowerActivity
import com.mager.gamer.ui.user.follow.FollowingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    private val viewModel: UserViewModel by viewModels()
    private lateinit var binding: ActivityProfileBinding
    private lateinit var dataFollow: Data
    private var donefollow = false
    private val postUserAdapter = PostinganAdapter(
        mutableListOf(),
        onDetailClick = { data, pos -> intentToDetail(data, pos)},
        onCopyClick = {},
        onVideoClick = {},
        onLikeClick = {},
    )
    private var lastPositionForUpdate = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getParcelable<Data>("post")?.let {
            dataFollow = it
            donefollow = it.status
            binding.txtName.text = it.createdBy.nama
            binding.txtUser.text = it.createdBy.username
            binding.txtBio.text = it.createdBy.biodata
            binding.txtLoc.text = it.createdBy.lokasi
            Glide.with(binding.imgPhoto.context)
                .load(it.createdBy.fotoProfile)
                .error(R.drawable.logo_mager_1)
                .into(binding.imgPhoto)
            if (it.status) {
                binding.btnIkuti.visibility = View.GONE
                binding.btnMengikuti.visibility = View.VISIBLE
            } else {
                binding.btnIkuti.visibility = View.VISIBLE
                binding.btnMengikuti.visibility = View.GONE
            }
            lifecycleScope.launch {
                viewModel.getAllFollowers(it.createdBy.id)
                viewModel.getAllFollowing(it.createdBy.id)
                viewModel.getUserDetail(it.createdBy.id)
                viewModel.getAllPost(it.createdBy.id)
            }
        }

        binding.recyclerPostingan.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postUserAdapter
        }
        binding.btnIkuti.setOnClickListener {
            lifecycleScope.launch {
                viewModel.followUser(
                    MagerSharedPref.userId!!, dataFollow.createdBy.id
                )
            }
        }
        binding.btnMengikuti.setOnClickListener {
            lifecycleScope.launch {
                viewModel.followUser(
                    MagerSharedPref.userId!!, dataFollow.createdBy.id)
            }
        }

        setupObserver()
    }
    private fun intentToDetail(
        content: Data,
        pos: Int
    ) {
        lastPositionForUpdate = pos
        val i = Intent(this, DetailPostinganActivity::class.java)
        i.putExtra("post", content)
        intentWithResult.launch(i)
    }

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


    private fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
//        viewModel.message.observe(this) {
//            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
//        }
        viewModel.followResponse.observe(this) {
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
            val size = it.data.content.size
            binding.txtNumbFollowing.text = size.toString()
            if (size > 0) {
                binding.linearFollowing.setOnClickListener {
                    val i = Intent(this, FollowingActivity::class.java)
                    i.putExtra("userId", dataFollow.id)
                    startActivity(i)
                }
            }
        }
        viewModel.followerResponse.observe(this) {
            val size = it.data.content.size
            binding.txtNumbFollower.text = size.toString()
            if (size > 0) {
                binding.linearFollower.setOnClickListener {
                    val i = Intent(this, FollowerActivity::class.java)
                    i.putExtra("userId", dataFollow.id)
                    startActivity(i)
                }
            }
        }
        viewModel.postinganResponse.observe(this) {
            postUserAdapter.postingan.clear()
            postUserAdapter.postingan.addAll(it.data)
            postUserAdapter.notifyDataSetChanged()
        }

    }

}