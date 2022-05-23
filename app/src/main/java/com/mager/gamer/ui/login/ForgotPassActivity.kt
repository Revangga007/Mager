package com.mager.gamer.ui.login

import androidx.activity.viewModels
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.databinding.ActivityForgetPassBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPassActivity : BaseActivity(){
    private lateinit var binding: ActivityForgetPassBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun setupObserver() {
        TODO("Not yet implemented")
    }
}