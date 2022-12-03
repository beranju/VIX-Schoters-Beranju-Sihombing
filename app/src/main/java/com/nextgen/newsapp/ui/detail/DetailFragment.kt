package com.nextgen.newsapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nextgen.newsapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Read News"
//        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        (activity as AppCompatActivity?)?.supportActionBar?.setHomeButtonEnabled(true)

        val webView = _binding?.webView as WebView

        val url = arguments?.getString(URL).toString()
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                view?.loadUrl("javascript:alert('berita berhasil dimuat')")
            }
        }
//        webView.webChromeClient = object : WebChromeClient(){
//            override fun onJsAlert(
//                view: WebView?,
//                url: String?,
//                message: String?,
//                result: JsResult?
//            ): Boolean {
//                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//                result?.confirm()
//                return true
//            }
//        }
        webView.loadUrl(url)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        private val TAG = "DetailFragment"
        const val URL = "url"
    }
}