package com.nextgen.newsapp.ui.search

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.nextgen.newsapp.R
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.FragmentSearchBinding
import com.nextgen.newsapp.di.Injection
import com.nextgen.newsapp.helper.Async
import com.nextgen.newsapp.ui.ViewModelFactory
import com.nextgen.newsapp.ui.adapter.CarouselAdapter
import com.nextgen.newsapp.ui.adapter.SearchAdapter
import kotlin.math.abs


class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchAdapter
    private var listNews =  ArrayList<ArticlesItem>()
    private val viewModel by viewModels<SearchViewModel> {
        ViewModelFactory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Search News"
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back_arrow);
//        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        (activity as AppCompatActivity?)?.supportActionBar?.setHomeButtonEnabled(true)

        _binding?.rvSearch?.layoutManager = LinearLayoutManager(requireContext())
        _binding?.rvSearch?.setHasFixedSize(true)

        _binding?.search?.setOnKeyListener { _, keyKode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyKode == KeyEvent.KEYCODE_ENTER){
                val query = _binding?.search?.text.toString()
                viewModel.getSearchNews(query).observe(viewLifecycleOwner){result->
                    when(result){
                        is Async.Loading -> loading(true)
                        is Async.Error -> {
                            loading(false)
                            Log.e(TAG, "onFailure: ${result.error}")
                        }
                        is Async.Success -> {
                            loading(false)
                            _binding?.search?.clearFocus()
                            if (result.data.articles?.size == null){
                                _binding?.noSearch?.visibility = View.VISIBLE
                            }else{
                                result.data.articles.forEach {
                                    listNews.add(it!!)
                                }
                                _binding?.noSearch?.visibility = View.GONE
                                adapter = SearchAdapter(listNews)
                                _binding?.rvSearch?.adapter = adapter


                            }


                        }
                    }
                }


                return@setOnKeyListener true
            }

            return@setOnKeyListener false

        }


    }

    private fun loading(isLoading: Boolean) {
        if (isLoading){
            _binding?.pbSearch?.visibility = View.VISIBLE
        }else{
            _binding?.pbSearch?.visibility = View.GONE

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private val TAG = "SearchFragment"
    }
}