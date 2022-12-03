package com.nextgen.newsapp.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nextgen.newsapp.databinding.FragmentSaveNewsBinding
import com.nextgen.newsapp.ui.ViewModelFactory
import com.nextgen.newsapp.ui.adapter.SaveNewsAdapter


class SaveNewsFragment : Fragment() {
    private var _binding : FragmentSaveNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SaveNewsAdapter
    private val viewModel by viewModels<SaveNewsViewModel> {
        ViewModelFactory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        getSavedNews()


    }

    private fun getSavedNews() {
        viewModel.getSavedNews().observe(viewLifecycleOwner){result->
            adapter = SaveNewsAdapter{data->
                if (data.isSaved){
                    viewModel.unSaveNews(data)
                }else{
                    viewModel.saveNews(data)
                }
            }
            adapter.submitList(result)
            _binding?.rvSave?.adapter = adapter

        }
    }

    private fun setupRecyclerView() {
        _binding?.rvSave?.layoutManager = LinearLayoutManager(requireContext())
        _binding?.rvSave?.setHasFixedSize(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSaveNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "SaveNewsFragment"
    }
}