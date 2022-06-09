package com.mager.gamer.ui.komunitas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.R
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.komunitas.get.Komunitas
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.data.model.remote.postingan.get.LikedBy
import com.mager.gamer.databinding.ActivityDetailCommunityBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.home.PostinganAdapter
import com.mager.gamer.ui.login.LoginActivity
import com.mager.gamer.ui.postingan.BuatPostinganActivity
import com.mager.gamer.ui.postingan.DetailPostinganActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailCommunityActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailCommunityBinding
    private val viewModel: DetailCommunityViewModel by viewModels()
    private lateinit var communityData: Komunitas
    private val postAdapter = PostinganAdapter(mutableListOf(),
        onDetailClick = { data, pos -> intentToDetail(data, pos) },
        onCopyClick = {},
        onVideoClick = {},
        onLikeClick = {}
    )
    private var lastJoined = false
    private var lastPositionForUpdate = -1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getParcelable<Komunitas>("data")?.let {
            communityData = it
            lastJoined = it.joined
            binding.txtName.text = it.namaKomunitas
            binding.txtMember.text = "${it.jumlahAnggota} Anggota"
            if (it.joined) {
                binding.btnJoined.visibility = View.VISIBLE
                binding.btnJoin.visibility = View.GONE
                binding.constraintPost.visibility = View.VISIBLE
            } else {
                binding.btnJoined.visibility = View.GONE
                binding.btnJoin.visibility = View.VISIBLE
                binding.constraintPost.visibility = View.GONE
            }

            if (it.banner != null)
                Glide.with(this).load(it.banner).into(binding.imgPhoto)
        }
        binding.recyclerPost.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postAdapter
        }
        binding.btnJoin.setOnClickListener {
            lifecycleScope.launch {
                viewModel.joinCommunity(communityData.id)
            }
        }
        binding.btnJoined.setOnClickListener {
            lifecycleScope.launch {
                viewModel.joinCommunity(communityData.id)
            }
        }

        setupObserver()

        lifecycleScope.launch {
            viewModel.getAllPost(communityData.id)
        }
    }

    private fun intentToCreatePost(isImage: Boolean = false, isLive: Boolean = false) {
        val isLogin = MagerSharedPref.isLoggedIn
        if (!isLogin) {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            return
        }
        val intent = Intent(this, BuatPostinganActivity::class.java)
        if (isImage) intent.putExtra(BuatPostinganActivity.INTENT_IMAGE_MODE, true)
        else if (isLive) intent.putExtra(BuatPostinganActivity.INTENT_LIVE_MODE, true)
        startActivity(intent)
    }

    private fun intentToDetail(content: Data, pos: Int) {
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

                    (binding.recyclerPost.adapter as PostinganAdapter?)?.let {
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
            if (MagerSharedPref.fotoProfile == null) {
                Glide.with(this)
                    .load(R.drawable.logo_mager_1)
                    .into(binding.imgUser)
            } else {
                Glide.with(this)
                    .load(MagerSharedPref.fotoProfile)
                    .into(binding.imgUser)
            }
        }

    override fun setupObserver() {

        binding.cardPost.setOnClickListener {
            intentToCreatePost()
        }

        binding.cardUpload.setOnClickListener {
            intentToCreatePost(isImage = true)
        }
        binding.cardLive.setOnClickListener {
            intentToCreatePost(isLive = true)
        }
        val loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loadingUI.show() else loadingUI.dismiss()
        }
        viewModel.joinResponse.observe(this) {
            lastJoined = !lastJoined
            if (lastJoined) {
                Toast.makeText(this, "Berhasil bergabung", Toast.LENGTH_SHORT).show()
                binding.btnJoin.visibility = View.GONE
                binding.btnJoined.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Berhasil keluar", Toast.LENGTH_SHORT).show()
                binding.btnJoin.visibility = View.VISIBLE
                binding.btnJoined.visibility = View.GONE
            }
            setResult(RESULT_OK)
        }
        viewModel.postResponse.observe(this) {
            val lastPos = postAdapter.postingan.size - 1
            postAdapter.postingan.clear()
            postAdapter.notifyItemRangeRemoved(0, lastPos)
            postAdapter.postingan.addAll(it)
            postAdapter.notifyItemRangeInserted(0, it.size - 1)
        }
    }
}