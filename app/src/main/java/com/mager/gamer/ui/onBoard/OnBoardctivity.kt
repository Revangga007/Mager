package com.mager.gamer.ui.onBoard

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mager.gamer.databinding.ActivityOnBoardBinding
import com.mager.gamer.ui.login.LoginActivity
import com.mager.gamer.ui.register.RegisterActivity

class OnBoardctivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardBinding
    private val vp by lazy {
        binding.slideVP
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dotSlide = binding.springDotsIndicator
        val pagerAdapter = ViewPagerAdapter(this)
        vp.adapter = pagerAdapter
        dotSlide.setViewPager2(vp)

        binding.btnDaftar.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
            finish()
        }
        binding.btnMasuk.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }

    }
}