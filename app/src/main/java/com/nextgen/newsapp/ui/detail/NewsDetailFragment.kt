package com.nextgen.newsapp.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nextgen.newsapp.R
import com.nextgen.newsapp.data.remote.dto.ArticlesItem
import com.nextgen.newsapp.databinding.ContentScrollingBinding
import com.nextgen.newsapp.databinding.FragmentNewsDetailBinding

class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    private var _bindingContent : ContentScrollingBinding? = null
    private val binding get() = _binding!!
    private lateinit var content: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val articleData = arguments?.getParcelable<ArticlesItem>(ARTICLE_DATA)
        content = view.findViewById(R.id.content)

        setUpView(articleData!!)

        /**
         * ?fitur share
         */
//        shareBtn.setOnClickListener {
//            try {
//                val i = Intent(Intent.ACTION_SEND)
//                i.type = "text/plan"
//                i.putExtra(Intent.EXTRA_SUBJECT, args.article.title)
//                val body: String =
//                    args.article.title.toString() + "\n" + args.article.url + "\n" + "Share from the News App" + "\n"
//                i.putExtra(Intent.EXTRA_TEXT, body)
//                startActivity(Intent.createChooser(i, "Share with :"))
//            } catch (e: Exception) {
//                showToast("Sorry, \nCannot be share")
//            }
//        }


    }

    private fun setUpView(articleData: ArticlesItem) {

        content?.text = articleData.content
        Glide.with(requireContext())
            .load(articleData.urlToImage)
            .into(_binding?.ivThumbnail!!)
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