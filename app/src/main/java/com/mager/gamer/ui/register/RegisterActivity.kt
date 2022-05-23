package com.mager.gamer.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.mager.gamer.R
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.databinding.ActivityRegisterBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()
    private var selectedGender: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textField.requestFocus()

        binding.txtName.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() ->
                    binding.textField.error = "Nama tidak boleh kosong"
                else -> binding.textField.error = null
            }
            validateButton()
        }
        binding.txtEmail.doOnTextChanged { text, _, _, _ ->
            when {
                text.isNullOrEmpty() ->
                    binding.textField1.error = "Email tidak boleh kosong"
                else -> binding.textField1.error = null
            }
            validateButton()
        }
        binding.txtUsername.doOnTextChanged { text, _, _, _ ->
            when {
                text.isNullOrEmpty() ->
                    binding.textField2.error = "Username tidak boleh kosong"
                text.length < 3 || text.length > 15 ->
                    binding.textField2.error =
                        "Username minimal 3 karakter dan maksimal 15 karakter"
                else -> binding.textField2.error = null
            }
            validateButton()
        }
        binding.txtPw.doOnTextChanged { text, _, _, _ ->
            when {
                text.isNullOrEmpty() ->
                    binding.txtPassword.error = "Kata sandi tidak boleh kosong"
                text.length < 8 || !text.toString().matches("[a-zA-Z0-9]+".toRegex()) ->
                    binding.txtPassword.error =
                        "Kata sandi minimal 8 karakter kombinasi huruf dan angka"
                !text.isNullOrEmpty() && text.toString() != binding.txtKonfirPw.text.toString() ->
                    binding.txtKonfir.error = "Konfirmasi kata sandi tidak sama"
                else -> binding.txtPassword.error = null
            }
            validateButton()
        }
        binding.txtKonfirPw.doOnTextChanged { text, _, _, _ ->
            when {
                text.isNullOrEmpty() ->
                    binding.txtKonfir.error = "Konfirmasi sandi tidak boleh kosong"
                !text.isNullOrEmpty() && text.toString() != binding.txtPw.text.toString() ->
                    binding.txtKonfir.error = "Konfirmasi kata sandi tidak sama"
                else -> binding.txtKonfir.error = null
            }
            validateButton()
        }
        binding.btnLaki.setOnClickListener {
            switchGender(true)
        }
        binding.btnGirl.setOnClickListener {
            switchGender(false)
        }
        binding.btnDaftar.setOnClickListener {
            val textGender = if (selectedGender!!) binding.btnLaki.text.toString()
            else binding.btnGirl.text.toString()

            lifecycleScope.launch {
                viewModel.register(
                    nama = binding.txtName.text.toString(),
                    email = binding.txtEmail.text.toString().trim(),
                    username = binding.txtUsername.text.toString().trim(),
                    password = binding.txtPw.text.toString().trim(),
                    gender = textGender
                )
            }
        }
        setupObserver()
    }

    private fun validateButton() {
        val nameOK = binding.textField.error == null
        val emailOK = binding.textField1.error == null
        val usernameOK = binding.textField2.error == null
        val passOK = binding.txtPassword.error == null
        val konfirOK = binding.txtKonfir.error == null
        val genderOK = selectedGender != null // alias udah milih gender
        binding.btnDaftar.isEnabled =
            nameOK && emailOK && usernameOK && passOK && konfirOK && genderOK
    }

    override fun setupObserver() {
        val loadingUi = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loadingUi.show() else loadingUi.dismiss()
        }
        viewModel.message.observe(this) {
            showMessageDialog(it)
        }
        viewModel.register.observe(this) {
            Toast.makeText(this@RegisterActivity, "Berhasil Daftar", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    /** boy: true, girl: false **/
    private fun switchGender(isBoy: Boolean) {
        if (isBoy) {
            // bikin tombol boy jadi garis biru
            binding.btnLaki.iconTint = ContextCompat.getColorStateList(this, R.color.dark_blue)
            binding.btnLaki.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnLaki.strokeWidth = 4
            binding.btnLaki.strokeColor = ContextCompat.getColorStateList(this, R.color.dark_blue)

            binding.btnGirl.iconTint = ContextCompat.getColorStateList(this, R.color.white_inactive)
            binding.btnGirl.setTextColor(ContextCompat.getColor(this, R.color.white_inactive))
            binding.btnGirl.strokeWidth = 0
            binding.btnGirl.strokeColor = ContextCompat.getColorStateList(this, R.color.dark_mode_3)
            selectedGender = true
        } else {
            // bikin tombol girl jadi garis pink
            binding.btnGirl.iconTint = ContextCompat.getColorStateList(this, R.color.pink)
            binding.btnGirl.setTextColor(ContextCompat.getColor(this, R.color.white))
            binding.btnGirl.strokeWidth = 4
            binding.btnGirl.strokeColor = ContextCompat.getColorStateList(this, R.color.pink)

            binding.btnLaki.iconTint = ContextCompat.getColorStateList(this, R.color.white_inactive)
            binding.btnLaki.setTextColor(ContextCompat.getColor(this, R.color.white_inactive))
            binding.btnLaki.strokeWidth = 0
            binding.btnLaki.strokeColor = ContextCompat.getColorStateList(this, R.color.dark_mode_3)
            selectedGender = false
        }
        validateButton()
    }
}

