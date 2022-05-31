package com.mager.gamer.ui.onBoard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mager.gamer.OnBoardFragment

class ViewPagerAdapter (Fa: FragmentActivity,
): FragmentStateAdapter(Fa) {
    private val dataFragments= mutableListOf(
        OnBoardFragment.newInstance("0"),
        OnBoardFragment.newInstance("1"),
        OnBoardFragment.newInstance("2"),

    )
    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment = dataFragments[position]
}