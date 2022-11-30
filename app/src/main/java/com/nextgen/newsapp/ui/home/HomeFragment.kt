package com.nextgen.newsapp.ui.home

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.nextgen.newsapp.R
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.FragmentHomeBinding
import com.nextgen.newsapp.helper.Async
import com.nextgen.newsapp.ui.ViewModelFactory
import com.nextgen.newsapp.ui.adapter.CarouselAdapter
import com.nextgen.newsapp.ui.adapter.LatestAdapter
import com.nextgen.newsapp.ui.adapter.SearchAdapter
import kotlin.math.abs

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var dataCarousel = ArrayList<ArticlesItem>()
    private val dataLatest = ArrayList<ArticlesItem>()
    private lateinit var mAdapter: LatestAdapter
    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.viewPagerCorousel?.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        _binding?.rvLatest?.layoutManager = LinearLayoutManager(requireContext())
        _binding?.rvLatest?.setHasFixedSize(true)

        goToSearch()
        getHeadlineNews()
        getLatestNews()





    }

    private fun getLatestNews() {
        viewModel.getLatestNews("general").observe(viewLifecycleOwner){result->
            when(result){
                is Async.Loading -> loading(true)
                is Async.Error -> {
                    loading(false)
                    Log.e(TAG, "onFailure: ${result.error}")
                }
                is Async.Success -> {
                    loading(false)
                    result.data.articles?.forEach { data->
                       dataLatest.add(data!!)
                    }
                    mAdapter = LatestAdapter(dataLatest)
                    _binding?.rvLatest?.adapter = mAdapter

                }
            }
        }
    }

    private fun getHeadlineNews() {
        viewModel.getHeadlineNews().observe(viewLifecycleOwner){result->
            when(result){
                is Async.Loading -> loading(true)
                is Async.Error -> {
                    loading(false)
                    Log.e(TAG, "onFailure: ${result.error}")
                }
                is Async.Success -> {
                    loading(false)
                    result.data.articles?.forEach { data->
                        dataCarousel.add(data!!)
                        _binding?.viewPagerCorousel?.adapter = CarouselAdapter(dataCarousel)
                        val compositePageTransformer = CompositePageTransformer()
                        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
                        compositePageTransformer.addTransformer { page, position ->
                            val r = 1- abs(position)
                            page.scaleY = (0.80f + r * 0.20f)
                        }
                        _binding?.viewPagerCorousel?.setPageTransformer(compositePageTransformer)
                    }

                }
            }
        }
    }

    private fun goToSearch() {
        _binding?.ivSearch?.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_searchFragment)
        }
    }

    private fun loading(isLoading: Boolean) {
        if (isLoading){
            _binding?.pbMain?.visibility = View.VISIBLE
        }else{
            _binding?.pbMain?.visibility = View.GONE

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private val TAG = "HomeFragment"

    }


}
