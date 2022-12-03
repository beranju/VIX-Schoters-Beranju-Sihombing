package com.nextgen.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.nextgen.newsapp.R
import com.nextgen.newsapp.databinding.FragmentNewsBinding
import com.nextgen.newsapp.ui.adapter.SectionPagerAdapter

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sectionPagerAdapter = SectionPagerAdapter(requireActivity() as AppCompatActivity)
        val viewPager = _binding?.viewPager as ViewPager2
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = _binding?.tabs!!
        TabLayoutMediator(tabs, viewPager){tab, position->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private val TAG = "NewsFragment"

        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
            R.string.tab_text_3,
            R.string.tab_text_4,
            R.string.tab_text_5,
            R.string.tab_text_6
//            R.drawable.ic_bussiness_24,
//            R.drawable.ic_entertainment,
//            R.drawable.ic_health,
//            R.drawable.ic_science,
//            R.drawable.ic_sport,
//            R.drawable.ic_tech
        )

    }
}