package com.nextgen.newsapp.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nextgen.newsapp.data.remote.dto.UserResponse
import com.nextgen.newsapp.databinding.FragmentProfileBinding
import com.nextgen.newsapp.helper.Async
import com.nextgen.newsapp.ui.ViewModelFactory
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.getGithubProfile().observe(viewLifecycleOwner){result->
                when(result){
                    is Async.Loading ->{
                        _binding?.btnToGithub?.isEnabled = false
                        _binding?.btnToGithub?.text = "loading..."
                    }
                    is Async.Error -> {
                        Log.e("ProfileFragment", result.error)
                    }
                    is Async.Success ->{
                        _binding?.btnToGithub?.isEnabled = true
                        setUpView(result.data)
                    }

                }

            }

        }



    }

    private fun setUpView(data: UserResponse) {
        _binding?.tvLogin?.text = data.login
        _binding?.tvName?.text = data.name
        _binding?.tvCompany?.text = data.company
        _binding?.tvLocation?.text = data.location
        _binding?.follower?.text = data.followers.toString()
        _binding?.following?.text = data.following.toString()
        _binding?.repository?.text = data.publicRepos.toString()
        Glide.with(requireContext())
            .load(data.avatarUrl)
            .circleCrop()
            .into(_binding?.photo!!)
        _binding?.btnToGithub?.text = "Visit my Github"
        _binding?.btnToGithub?.setOnClickListener {
            val url = Uri.parse(data.html_url)
            val intentToGithub = Intent(Intent.ACTION_VIEW, url)
            startActivity(intentToGithub)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}