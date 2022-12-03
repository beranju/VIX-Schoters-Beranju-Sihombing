package com.nextgen.newsapp.ui.search

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.FragmentSearchBinding
import com.nextgen.newsapp.helper.Async
import com.nextgen.newsapp.ui.ViewModelFactory
import com.nextgen.newsapp.ui.adapter.SearchAdapter


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchAdapter
    private var listNews = ArrayList<ArticlesItem>()
    private val viewModel by viewModels<SearchViewModel> {
        ViewModelFactory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Search News"

        _binding?.rvSearch?.layoutManager = LinearLayoutManager(requireContext())
        _binding?.rvSearch?.setHasFixedSize(true)

        _binding?.search?.setOnKeyListener { _, keyKode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyKode == KeyEvent.KEYCODE_ENTER) {
                val query = _binding?.search?.text.toString()
                viewModel.getSearchNews(query).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Async.Loading -> loading(true)
                        is Async.Error -> {
                            loading(false)
                            Log.e(TAG, "onFailure: ${result.error}")
                        }
                        is Async.Success -> {
                            loading(false)
                            _binding?.search?.clearFocus()
                            if (result.data.articles?.size == null) {
                                _binding?.noSearch?.visibility = View.VISIBLE
                            } else {
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
        if (isLoading) {
            _binding?.pbSearch?.visibility = View.VISIBLE
        } else {
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

    companion object {
        private val TAG = "SearchFragment"
    }
}