package com.mager.gamer.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.data.model.remote.password.ForgetPassBody
import com.mager.gamer.data.model.remote.password.ForgetPassResponse
import com.mager.gamer.databinding.ActivityEditPostBinding
import com.mager.gamer.databinding.ActivityForgetPassBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.register.RegisterActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPassActivity : BaseActivity(){
    private lateinit var binding: ActivityForgetPassBinding
    private val viewModel: ForgetPassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgLeft.setOnClickListener {
            finish()
        }
        binding.btnSimpan.setOnClickListener{
            lifecycleScope.launch {
                val email = binding.txtEmail.text.toString().trim()
                viewModel.submitEmail(email)
            }
            val i = Intent(this, DoneActivity::class.java)
            startActivity(i)
        }

        setupObserver()
}
    override fun setupObserver() {
        val loading = CustomLoadingDialog(this)
        viewModel.loading.observe(this) {
            if (it) loading.show() else loading.dismiss()
        }
        viewModel.message.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
        viewModel.submit.observe(this){
            Toast.makeText(this, "sedang memproses data", Toast.LENGTH_SHORT).show()
        }
    }
}