package com.mager.gamer.ui.onBoard

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mager.gamer.OnBoardFragment

class ViewPagerAdapter (Fa: FragmentActivity, listener:(CharSequence)->Unit
): FragmentStateAdapter(Fa) {
    private val dataFragments= mutableListOf(
        OnBoardFragment.newInstance("0",listener),
        OnBoardFragment.newInstance("1",listener),
        OnBoardFragment.newInstance("2",listener),

    )
    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment =dataFragments[position]
}