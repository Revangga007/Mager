package com.mager.gamer.ui.postingan

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mager.gamer.R
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.databinding.ActivityDetailPostinganBinding
import com.mager.gamer.databinding.ItemSheetBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class DetailPostinganActivity : AppCompatActivity() {

    private val viewModel : DetailPostinganViewModel by viewModels()
    private lateinit var postingan : Data
    private lateinit var sheetBinding: ItemSheetBinding
    private var like = 0

    private lateinit var binding: ActivityDetailPostinganBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostinganBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sheetBinding = ItemSheetBinding.inflate(layoutInflater)

        val sheetDialog = BottomSheetDialog(this, R.style.backgroundSheet)
        sheetDialog.setContentView(sheetBinding.root)

        intent.extras?.getParcelable<Data>("post")?.let {
            postingan = it
            like = it.jumlahLike
            binding.txtPosting.text = it.postText
            binding.recyclerKomen.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = KomentarAdapter(it.komentarBy.toMutableList()){sheetDialog.show()}
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
                like++
                binding.icLike.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_liked))
            } else {
                like--
                binding.icLike.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_like_outline))
            }
        }
    }

    override fun onDestroy() {
        val i = Intent()
        i.putExtra("like", like)
        i.putExtra("position", intent.getIntExtra("position", 0))
        setResult(Activity.RESULT_OK, i)
        super.onDestroy()
    }


}