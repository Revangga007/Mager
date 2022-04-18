package com.mager.gamer.ui.postingan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mager.gamer.MainActivity
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.databinding.ActivityDetailPostinganBinding
import com.mager.gamer.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class DetailPostinganActivity : AppCompatActivity() {

    private val viewModel : DetailPostinganViewModel by viewModels()
    private lateinit var postingan : Data

    private lateinit var binding: ActivityDetailPostinganBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostinganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.getParcelable<Data>("post")?.let {
            postingan = it
            binding.txtPosting.text = it.postText
            binding.recyclerKomen.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = KomentarAdapter(it.komentarBy.toMutableList())
            }
        }

        binding.btnLike.setOnClickListener {
            lifecycleScope.launch {
                viewModel.likePostingan(postingan.id)
            }
        }

        binding.imgLeft.setOnClickListener {
            finish()
        }
        setupObserver()
    }

    private fun setupObserver(){
        viewModel.likeResult.observe(this) {
            if (it.data.reaction) {
                binding.icLike.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_liked))
            } else {
                binding.icLike.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_like))
            }
        }
    }


}