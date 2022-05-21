package com.mager.gamer.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.mager.gamer.MainActivity
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.databinding.ActivityRegisterBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textField.requestFocus()
        binding.textField.error = "Nama tidak boleh kosong"
        binding.textField1.error = "Email sudah terdaftar"
        binding.textField2.error = "Username sudah terdaftar"
        binding.txtPassword.error = "Kata sandi tidak boleh kurang dari 8 karakter"

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
                text.toString().isNullOrEmpty() ->
                    binding.textField1.error = "Email sudah terdaftar"
                else -> binding.textField1.error = null
            }
            validateButton()
        }
        binding.txtUsername.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isNullOrEmpty() ->
                    binding.textField2.error = "Username sudah terdaftar"
                else -> binding.textField2.error = null
            }
            validateButton()
        }
        binding.txtPw.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() ->
                    binding.txtPassword.error = "Kata sandi tidak boleh kurang dari 8 karakter"
                else -> binding.txtPassword.error = null
            }
            validateButton()
        }
        binding.btnDaftar.setOnClickListener {
            lifecycleScope.launch {
                viewModel.register(
                    nama = binding.txtName.text.toString(),
                    email = binding.txtEmail.text.toString().trim(),
                    username = binding.txtUsername.text.toString().trim(),
                    password = binding.txtPw.text.toString().trim(),
                    gender = binding.btnGirl.text.toString()
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
        binding.btnDaftar.isEnabled = nameOK && emailOK && usernameOK &&  passOK && konfirOK
    }
    override fun setupObserver() {
        val loadingUi = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loadingUi.show() else loadingUi.dismiss()
        }
        viewModel.register.observe(this) {
            Toast.makeText(this@RegisterActivity, "Berhasil Daftar", Toast.LENGTH_SHORT).show()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finishAffinity()
        }
    }


}

