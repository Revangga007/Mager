package com.mager.gamer.ui.postingan

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import com.mager.gamer.TimeAgo.toTimeAgo
import com.mager.gamer.data.local.MagerSharedPref
import com.mager.gamer.data.model.remote.postingan.get.Data
import com.mager.gamer.data.model.remote.postingan.get.KomentarBy
import com.mager.gamer.databinding.*
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.user.profile.ProfileActivity
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
    private lateinit var reportBinding: ItemSheetReportBinding
    private lateinit var sheetOtherBinding: ItemSheetNonUserBinding
    private var komentarAdapter = KomentarAdapter(mutableListOf()) {
        targetCommentId = it.id
        val idUser = MagerSharedPref.userId
        sheetDialog.apply {
            if (postingan.createdBy.id == idUser) {
                isComment = true
                sheetBinding.linearEdit.visibility = View.GONE
                setContentView(sheetBinding.root)
                show()
            }
        }
    }

    private var isComment = false
    private var targetCommentId = -1
    private var like = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailPostinganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sheetBinding = ItemSheetBinding.inflate(layoutInflater)
        sheetOtherBinding = ItemSheetNonUserBinding.inflate(layoutInflater)
        deleteDialogBinding = DeleteDialogBinding.inflate(layoutInflater)
        deletePostBinding = DeletePostDialogBinding.inflate(layoutInflater)
        sheetDialog = BottomSheetDialog(this, R.style.backgroundSheet)

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
            if (it.files?.split(".")?.last() == "mp4") {
                binding.imgPosting.setOnClickListener {
                    val i = Intent(this, VideoActivity::class.java)
                    i.putExtra(VideoActivity.INTENT_VIDEO_URL, postingan.files)
                    startActivity(i)
                }
            }
            binding.txtNama.text = it.createdBy.nama
            binding.txtUsername.text = it.createdBy.username
            binding.txtWaktu.text = it.createdDate.toTimeAgo()
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
        if (MagerSharedPref.fotoProfile == null) {
            Glide.with(this)
                .load(R.drawable.logo_mager_1)
                .into(binding.imgFoto2)
        } else {
            Glide.with(this)
                .load(MagerSharedPref.fotoProfile)
                .into(binding.imgFoto2
                )
        }

        if (postingan.createdBy.fotoProfile == null) {
            Glide.with(binding.imgFoto.context)
                .load(R.drawable.logo_mager_1)
                .into(binding.imgFoto)
        } else {
            Glide.with(binding.imgFoto.context)
                .load(postingan.createdBy.fotoProfile)
                .into(binding.imgFoto)
        }

        val deleteDialog = Dialog(this)
        deleteDialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val idUser = MagerSharedPref.userId
        binding.btnOption.setOnClickListener {
            isComment = false
            sheetDialog.apply {
                sheetBinding.linearEdit.visibility = View.VISIBLE
                setContentView(
                    if (postingan.createdBy.id == idUser) sheetBinding.root
                    else sheetOtherBinding.root
                )
                show()
            }
        }

       binding.txtNama.setOnClickListener {
           val i = Intent(this, ProfileActivity::class.java)
           i.putExtra("post", postingan)
           startActivity(i)
       }
        binding.txtUsername.setOnClickListener {
           val i = Intent(this, ProfileActivity::class.java)
           i.putExtra("post", postingan)
           startActivity(i)
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
        sheetBinding.linearEdit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra("post", postingan)
            startActivity(intent)
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
        binding.btnCopy.setOnClickListener {
            val clipboard: ClipboardManager =
                (this).getSystemService(Context.CLIPBOARD_SERVICE)
                        as ClipboardManager
            val clip = ClipData.newPlainText("link berhasil disalin", postingan.linkPostingan)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, "Link berhasil disalin", Toast.LENGTH_LONG)
                .show()
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
            finish()
        }
    }
}


