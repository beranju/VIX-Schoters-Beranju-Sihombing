package com.nextgen.newsapp.ui.news

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.FragmentLatesBinding
import com.nextgen.newsapp.helper.Async
import com.nextgen.newsapp.ui.ViewModelFactory
import com.nextgen.newsapp.ui.adapter.LatestAdapter
import com.nextgen.newsapp.ui.adapter.NewsAdapter
import com.nextgen.newsapp.ui.home.HomeFragment


class LatestFragment : Fragment() {
    private var _binding: FragmentLatesBinding? = null
    private val binding get() = _binding!!
    private lateinit var tabName: String
    private lateinit var mAdapter: NewsAdapter
    private var dataLatest = ArrayList<ArticlesItem>()
    private val viewModel by viewModels<NewsViewModel> {
        ViewModelFactory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var tabTitle = _binding?.tabName!!
        _binding?.rvNews?.layoutManager = LinearLayoutManager(requireContext())
        _binding?.rvNews?.setHasFixedSize(true)

//        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
        tabName = arguments?.getString(ARG_SECTION_NUMBER).toString()
        when(tabName){
            TAB_BUSINESS -> {
                tabTitle.text = "Business News"
                viewModel.getCategoryNews("business").observe(viewLifecycleOwner){result->
                    when(result){
                        is Async.Loading -> loading(true)
                        is Async.Error -> {
                            loading(false)
                            Log.e(TAG, "onFailure: ${result.error}")
                        }
                        is Async.Success -> {
                            loading(false)
                            dataLatest.clear()
                            result.data.articles?.forEach {data->
                                dataLatest.add(data!!)
                            }
                            mAdapter = NewsAdapter(dataLatest)
                            _binding?.rvNews?.adapter = mAdapter


                        }
                    }
                }
            }
            TAB_ENTERTAINMENT -> {
                tabTitle.text = "Entertainment News"
                viewModel.getCategoryNews("entertainment").observe(viewLifecycleOwner){result->
                    when(result){
                        is Async.Loading -> loading(true)
                        is Async.Error -> {
                            loading(false)
                            Log.e(TAG, "onFailure: ${result.error}")
                        }
                        is Async.Success -> {
                            loading(false)
                            dataLatest.clear()
                            result.data.articles?.forEach {data->
                                dataLatest.add(data!!)
                            }
                            mAdapter = NewsAdapter(dataLatest)
                            _binding?.rvNews?.adapter = mAdapter


                        }
                    }
                }
            }
            TAB_HEALTH -> {
                tabTitle.text = "Health News"
                viewModel.getCategoryNews("health").observe(viewLifecycleOwner){result->
                    when(result){
                        is Async.Loading -> loading(true)
                        is Async.Error -> {
                            loading(false)
                            Log.e(TAG, "onFailure: ${result.error}")
                        }
                        is Async.Success -> {
                            loading(false)
                            dataLatest.clear()
                            result.data.articles?.forEach {data->
                                dataLatest.add(data!!)
                            }
                            mAdapter = NewsAdapter(dataLatest)
                            _binding?.rvNews?.adapter = mAdapter


                        }
                    }
                }
            }
            TAB_SCIENCE -> {
                tabTitle.text = "Science News"
                viewModel.getCategoryNews("science").observe(viewLifecycleOwner){result->
                    when(result){
                        is Async.Loading -> loading(true)
                        is Async.Error -> {
                            loading(false)
                            Log.e(TAG, "onFailure: ${result.error}")
                        }
                        is Async.Success -> {
                            loading(false)
                            dataLatest.clear()
                            result.data.articles?.forEach {data->
                                dataLatest.add(data!!)
                            }
                            mAdapter = NewsAdapter(dataLatest)
                            _binding?.rvNews?.adapter = mAdapter


                        }
                    }
                }
            }
            TAB_SPORT -> {
                tabTitle.text = "Sports News"
                viewModel.getCategoryNews("sports").observe(viewLifecycleOwner){result->
                    when(result){
                        is Async.Loading -> loading(true)
                        is Async.Error -> {
                            loading(false)
                            Log.e(TAG, "onFailure: ${result.error}")
                        }
                        is Async.Success -> {
                            loading(false)
                            dataLatest.clear()
                            result.data.articles?.forEach {data->
                                dataLatest.add(data!!)
                            }
                            mAdapter = NewsAdapter(dataLatest)
                            _binding?.rvNews?.adapter = mAdapter


                        }
                    }
                }
            }
            TAB_TECH -> {
                tabTitle.text = "Technology News"
                viewModel.getCategoryNews("technology").observe(viewLifecycleOwner){result->
                    when(result){
                        is Async.Loading -> loading(true)
                        is Async.Error -> {
                            loading(false)
                            Log.e(TAG, "onFailure: ${result.error}")
                        }
                        is Async.Success -> {
                            loading(false)
                            dataLatest.clear()
                            result.data.articles?.forEach {data->
                                dataLatest.add(data!!)
                            }
                            mAdapter = NewsAdapter(dataLatest)
                            _binding?.rvNews?.adapter = mAdapter


                        }
                    }
                }
            }
        }



    }

    private fun loading(isLoading: Boolean) {
        if (isLoading){
            _binding?.pbLatest?.visibility = View.VISIBLE
        }else{
            _binding?.pbLatest?.visibility = View.GONE

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLatesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val TAG = "LatestFragment"
        const val ARG_SECTION_NUMBER = "section_number"
        const val TAB_BUSINESS = "business"
        const val TAB_ENTERTAINMENT = "entertainment"
        const val TAB_HEALTH = "health"
        const val TAB_SCIENCE = "science"
        const val TAB_SPORT = "sport"
        const val TAB_TECH = "tech"

    }
}