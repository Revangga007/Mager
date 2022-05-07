package com.mager.gamer.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mager.gamer.MainActivity
import com.mager.gamer.R
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.databinding.ActivityLoginBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.home.HomeFragment
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

        binding.btnMasuk.setOnClickListener {
            if (binding.txtUsername.text != null && binding.txtPw.text != null) {
                lifecycleScope.launch {
                    viewModel.login(
                        binding.txtUsername.text.toString(),
                        binding.txtPw.text.toString()
                    )
                }
            }
        }

    }

    override fun setupObserver() {
        val loadingUi = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loadingUi.show() else loadingUi.dismiss()
        }
        viewModel.errorLog.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.succesData.observe(this) {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}