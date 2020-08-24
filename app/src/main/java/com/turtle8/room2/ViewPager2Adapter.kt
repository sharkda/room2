package com.turtle8.room2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPager2Adapter (
    activity: FragmentActivity, val pageCount:Int
):FragmentStateAdapter(activity){
    override fun getItemCount(): Int = pageCount

    override fun createFragment(position: Int): Fragment
    = OnePageFragment.newInstance(position)
}