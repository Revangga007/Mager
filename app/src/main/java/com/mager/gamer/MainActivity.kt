package com.mager.gamer

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.databinding.ActivityMainBinding
import com.mager.gamer.dialog.CustomLoadingDialog
import com.mager.gamer.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navView.itemIconTintList = null
        loadingUI = CustomLoadingDialog(this)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }

    override fun setupObserver() {
        homeViewModel.message.observe(this) {
            showMessageToast(it)
        }
        homeViewModel.loading.observe(this) {
            if (it) showLoading() else hideLoading()
        }
    }
}


