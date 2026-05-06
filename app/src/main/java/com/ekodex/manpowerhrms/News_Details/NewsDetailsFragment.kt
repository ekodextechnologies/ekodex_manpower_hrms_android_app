package com.ekodex.manpowerhrms.News_Details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.ekodex.manpowerhrms.R
import com.ekodex.manpowerhrms.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : Fragment() {

    lateinit var binding:FragmentNewsDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)

        var args = NewsDetailsFragmentArgs.fromBundle(requireArguments())

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webViewClient = WebViewClient()
        binding.webview.loadUrl(args.keyword)

        return binding.root

    }


}