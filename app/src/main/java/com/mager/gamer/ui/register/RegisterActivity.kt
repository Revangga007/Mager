package com.mager.gamer.ui.register

import android.os.Bundle
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun setupObserver() {
        TODO("Not yet implemented")
    }
}