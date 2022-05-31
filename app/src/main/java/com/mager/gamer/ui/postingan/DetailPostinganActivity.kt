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
import com.mager.gamer.databinding.DeletePostDialogBinding
import com.mager.gamer.databinding.ItemSheetBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailPostinganActivity : AppCompatActivity() {

    private val viewModel: DetailPostinganViewModel by viewModels()
    private lateinit var binding: ActivityDetailPostinganBinding
    private lateinit var postingan: Data
    private lateinit var sheetBinding: ItemSheetBinding
    private lateinit var deleteDialogBinding: DeleteDialogBinding
    private lateinit var sheetDialog: BottomSheetDialog
    private lateinit var deletePostBinding: DeletePostDialogBinding
    private var komentarAdapter = KomentarAdapter(mutableListOf()) {
        targetCommentId = it.id
        isComment = true
        sheetDialog.show()
    }

    private var isComment = false
    private var targetCommentId = -1
    private var like = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPostinganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sheetBinding = ItemSheetBinding.inflate(layoutInflater)
        deleteDialogBinding = DeleteDialogBinding.inflate(layoutInflater)
        deletePostBinding = DeletePostDialogBinding.inflate(layoutInflater)
        sheetDialog = BottomSheetDialog(this, R.style.backgroundSheet)
        sheetDialog.setContentView(sheetBinding.root)

        intent.extras?.getParcelable<Data>("post")?.let {
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
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                komentarAdapter.comments.addAll(it.komentarBy)
                adapter = komentarAdapter
            }

            val idUser = MagerSharedPref.userId!!
            it.likedBy.find { like ->
                like.user.id == idUser
            }?.let {
                binding.icLike.setImageResource(R.drawable.ic_liked)
            }
        }


        val deleteDialog = Dialog(this)
        deleteDialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        binding.btnOption.setOnClickListener {
            isComment = false
            sheetDialog.show()
        }
        sheetBinding.linearDelete.setOnClickListener {
            if (isComment) {
                deleteDialog.apply {
                    setContentView(deleteDialogBinding.root)
                    show()
                }
            } else {
                deleteDialog.apply {
                    setContentView(deletePostBinding.root)
                    show()
                }
            }
            sheetDialog.dismiss()
        }
        deleteDialogBinding.btnCanclePost.setOnClickListener {
            deleteDialog.dismiss()
        }
        deleteDialogBinding.btnHapusKomen.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteKomentar(targetCommentId)
            }
            deleteDialog.dismiss()
        }
        deletePostBinding.btnCanclePost.setOnClickListener {
            deleteDialog.dismiss()
        }
        deletePostBinding.btnHapusPost.setOnClickListener {
            lifecycleScope.launch {
                viewModel.deleteThisPost(postingan.id)
            }
            deleteDialog.dismiss()
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
            val postText = binding.edtKoment.text.toString().trim()
            lifecycleScope.launch {
                viewModel.komentarPostingan(postingan.id, postText)
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
            val comment = KomentarBy(
                it.data.createdDate, null,
                it.data.id, it.data.isiKomentar, it.data.updatedDate, it.data.user
            )

            komentarAdapter.comments.add(comment)
            komentarAdapter.notifyItemInserted(komentarAdapter.comments.size - 1)
            binding.edtKoment.setText("")
        }
        viewModel.delKomen.observe(this) {
            var pos = -1
            komentarAdapter.comments.forEachIndexed { index, comment ->
                if (comment.id == targetCommentId) {
                    pos = index
                    targetCommentId = -1
                    return@forEachIndexed
                }
            }
            if (pos != -1) {
                komentarAdapter.comments.removeAt(pos)
                komentarAdapter.notifyItemRemoved(pos)
            }

            Toast.makeText(this, "komentar telah dihapus", Toast.LENGTH_SHORT).show()
        }
        viewModel.deletePost.observe(this) {
            Toast.makeText(this, "postingan berhasil dihapus", Toast.LENGTH_SHORT).show()
        }
    }
}


