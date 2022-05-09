package com.mager.gamer.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.mager.gamer.MainActivity
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.databinding.ActivityLoginBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textField.requestFocus()
        binding.textField.error = "Email tidak boleh kosong"
        binding.txtPassword.error = "Password tidak boleh kosong"

        binding.txtUsername.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() ->
                    binding.textField.error = "Email tidak boleh kosong"
//                !Patterns.EMAIL_ADDRESS.matcher(text ?: "").matches() ->
//                    binding.textField.error = "Format email tidak valid"
                else -> binding.textField.error = null
            }
            validateButton()
        }
        binding.txtPw.doOnTextChanged { text, _, _, _ ->
            when {
                text.toString().isEmpty() -> {
                    binding.txtPassword.error = "Password tidak boleh kosong"
                }
                else -> binding.txtPassword.error = null
            }
            validateButton()
        }

        binding.btnMasuk.setOnClickListener {
            lifecycleScope.launch {
                viewModel.login(
                    binding.txtUsername.text.toString().trim(),
                    binding.txtPw.text.toString().trim()
                )
            }
        }

        setupObserver()
    }

    private fun validateButton() {
        val emailOK = binding.textField.error == null
        val passOK = binding.txtPassword.error == null
        binding.btnMasuk.isEnabled = emailOK && passOK
    }

    override fun setupObserver() {
        val loadingUi = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loadingUi.show() else loadingUi.dismiss()
        }
        viewModel.errorLog.observe(this) {
            binding.textField.error = it
//            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.succesData.observe(this) {
            Toast.makeText(this@LoginActivity, "Berhasil masuk", Toast.LENGTH_SHORT).show()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finishAffinity()
        }
    }
}