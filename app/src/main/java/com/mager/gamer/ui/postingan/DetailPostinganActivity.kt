package com.mager.gamer.ui.postingan

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mager.gamer.R
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.data.model.remote.postingan.get.KomentarBy
import com.mager.gamer.databinding.ActivityDetailPostinganBinding
import com.mager.gamer.databinding.DeleteDialogBinding
import com.mager.gamer.databinding.ItemSheetBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailPostinganActivity : AppCompatActivity() {

    private val viewModel: DetailPostinganViewModel by viewModels()
    private lateinit var postingan: Data
    private lateinit var sheetBinding: ItemSheetBinding
    private var like = 0
    private lateinit var deleteDialogBinding: DeleteDialogBinding
    var idPost = 0
    private var komentarAdapter: KomentarAdapter? = null

    private lateinit var binding: ActivityDetailPostinganBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPostinganBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sheetBinding = ItemSheetBinding.inflate(layoutInflater)
        deleteDialogBinding = DeleteDialogBinding.inflate(layoutInflater)

        val sheetDialog = BottomSheetDialog(this, R.style.backgroundSheet)
        sheetDialog.setContentView(sheetBinding.root)
        val deleteDialog = Dialog(this)
        deleteDialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(deleteDialogBinding.root)
        }
        binding.btnOption.setOnClickListener {
            sheetDialog.show()
        }
        sheetBinding.imgSampah.setOnClickListener {
            deleteDialog.show()
        }
        sheetBinding.txtDel.setOnClickListener {
            deleteDialog.show()
        }



        intent.extras?.getParcelable<Data>("post")?.let {
            idPost = it.id
            println(it.id)
            komentarAdapter = KomentarAdapter(it.komentarBy.toMutableList()) { sheetDialog.show() }
            postingan = it
            like = it.jumlahLike
            if (it.files != null) {
                Glide.with(binding.imgPosting.context)
                    .load(it.files)
                    .error(R.drawable.logo_mager_1)
                    .into(binding.imgPosting)
                binding.imgPosting.visibility = View.VISIBLE
            }
            binding.txtPosting.text = it.postText
            binding.txtLinkLive.text = it.linkLivestream
            binding.recyclerKomen.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false
                )
                adapter = komentarAdapter
            }

            val idUser = MagerSharedPref.userId!!
            it.likedBy.find { like ->
                like.user.id == idUser
            }?.let {
                binding.icLike.setImageResource(R.drawable.ic_liked)
            }
        }

        binding.btnLike1.setOnClickListener {
            lifecycleScope.launch {
                viewModel.likePostingan(postingan.id)
            }
        }

        binding.imgLeft.setOnClickListener {
            finish()
        }
        binding.imgSend.setOnClickListener {
            val postText = binding.edtKoment.text.toString()
            lifecycleScope.launch {
                viewModel.komentarPostingan(idPost, postText)
            }
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
        viewModel.likeResult.observe(this) {
            if (it.data.reaction) {
                like++
                binding.icLike.setImageResource(R.drawable.ic_liked)
            } else {
                like--
                binding.icLike.setImageResource(R.drawable.ic_like_outline)
            }
            val i = Intent()
            i.putExtra("like", like)
            i.putExtra("data", it.data)
            setResult(Activity.RESULT_OK, i)
        }
        viewModel.postComment.observe(this) {
            val comment = KomentarBy(it.data.createdDate, null,
                it.data.id,it.data.isiKomentar, it.data.updatedDate, it.data.user)

            komentarAdapter?.comments?.add(comment)
            komentarAdapter?.notifyDataSetChanged()
            binding.edtKoment.setText("")

            viewModel.message.observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
            deleteDialogBinding.btnHapusPost.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.deleteThisPost(idPost)
                }
            }


        }
    }
}


