package com.nextgen.newsapp.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.nextgen.newsapp.R
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.FragmentNewsDetailBinding
import com.nextgen.newsapp.helper.showToast

class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleData = arguments?.getParcelable<ArticlesItem>(ARTICLE_DATA)

        setUpView(articleData!!)
        _binding?.btnReadmore?.setOnClickListener {
            goToWebView(articleData.url)
        }

        _binding?.shareNews?.setOnClickListener {
            try {
                val i = Intent(Intent.ACTION_SEND)
                i.type = "text/plan"
                i.putExtra(Intent.EXTRA_SUBJECT, articleData.title)
                val body: String =
                    articleData.title.toString() + "\n" + articleData.url + "\n"
                i.putExtra(Intent.EXTRA_TEXT, body)
                startActivity(Intent.createChooser(i, "Share with :"))
            } catch (e: Exception) {
                context?.showToast(e.message.toString())
            }
        }


    }

    private fun goToWebView(url: String?) {
        val bundle = Bundle()
        bundle.putString(DetailFragment.URL, url.toString())
        findNavController().navigate(R.id.action_detailNewsFragment_to_detailFragment, bundle)
    }

    private fun setUpView(articleData: ArticlesItem) {
        _binding?.titleNews?.text = articleData.title
        _binding?.sourceName?.text = articleData.source?.name
        _binding?.date?.text = articleData.publishedAt
        _binding?.description?.text = buildString {
            append(articleData.description)
            append("...")
        }
        _binding?.description?.maxLines = 4
        _binding?.editor?.text = articleData.author

        Glide.with(requireContext())
            .load(articleData.urlToImage)
            .centerCrop()
            .into(_binding?.thumbnail!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARTICLE_DATA = "data"
    }
}