package com.nextgen.newsapp.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nextgen.newsapp.ui.news.LatestFragment

class SectionPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = LatestFragment()
        val bundle = Bundle()
        when(position){
            0 -> {
                bundle.putString(LatestFragment.ARG_SECTION_NUMBER, LatestFragment.TAB_BUSINESS)
            }
            1 -> {
                bundle.putString(LatestFragment.ARG_SECTION_NUMBER, LatestFragment.TAB_ENTERTAINMENT)
            }
            2 -> {
                bundle.putString(LatestFragment.ARG_SECTION_NUMBER, LatestFragment.TAB_HEALTH)
            }
            3 -> {
                bundle.putString(LatestFragment.ARG_SECTION_NUMBER, LatestFragment.TAB_SCIENCE)
            }
            4 -> {
                bundle.putString(LatestFragment.ARG_SECTION_NUMBER, LatestFragment.TAB_SPORT)
            }
            5 -> {
                bundle.putString(LatestFragment.ARG_SECTION_NUMBER, LatestFragment.TAB_TECH)
            }
        }
        fragment.arguments = bundle
        return fragment
    }
}