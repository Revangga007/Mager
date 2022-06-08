package com.mager.gamer.ui.search

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.mager.gamer.base.BaseActivity
import com.mager.gamer.databinding.ActivityLoginBinding
import com.mager.gamer.databinding.ActivitySearchBinding
import com.mager.gamer.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.slideVP.apply {
            this.adapter = ViewPagerAdapter(this@SearchActivity)
        }
        TabLayoutMediator(
            binding.tbLayout ,
            binding.slideVP) { tab, position ->
            if (position == 0) {
                tab.text = "Orang"
            } else {
                tab.text = "Komunitas"
            }
        }.attach()
    }

    override fun setupObserver() {
        TODO("Not yet implemented")
    }
}