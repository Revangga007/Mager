package com.mager.gamer.ui.komunitas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.data.model.remote.komunitas.get.Content
import com.mager.gamer.databinding.ActivityDetailCommunityBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.home.PostinganAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailCommunityActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailCommunityBinding
    private val viewModel: DetailCommunityViewModel by viewModels()
    private lateinit var communityData: Content
    private val postAdapter = PostinganAdapter(mutableListOf(),
        onDetailClick = { data, pos -> },
        onCopyClick = {}
    )

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getParcelable<Content>("data")?.let {
            communityData = it
            binding.txtName.text = it.namaKomunitas
            binding.txtMember.text = "${it.jumlahAnggota} Anggota"
            if (it.acceptance) {
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

        setupObserver()

        lifecycleScope.launch {
            viewModel.getAllPost(communityData.id)
        }
    }

    override fun setupObserver() {
        val loadingUI = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loadingUI.show() else loadingUI.dismiss()
        }
        viewModel.joinResponse.observe(this) {
            Toast.makeText(this, "Berhasil bergabung", Toast.LENGTH_SHORT).show()

            binding.btnJoin.visibility = View.GONE
            binding.btnJoined.visibility = View.VISIBLE
            setResult(RESULT_OK)
        }
        viewModel.postResponse.observe(this) {
            val lastPos = postAdapter.postingan.size - 1
            postAdapter.postingan.clear()
            postAdapter.notifyItemRangeRemoved(0, lastPos)
            postAdapter.postingan.addAll(it.data)
            postAdapter.notifyItemRangeInserted(0, it.data.size - 1)
        }
    }
}