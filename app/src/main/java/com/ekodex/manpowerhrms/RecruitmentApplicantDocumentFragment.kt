package com.ekodex.manpowerhrms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ekodex.manpowerhrms.Others.Internet
import com.ekodex.manpowerhrms.databinding.FragmentRecruitmentApplicantDocumentBinding

class RecruitmentApplicantDocumentFragment : Fragment() {

    lateinit var binding: FragmentRecruitmentApplicantDocumentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recruitment_applicant_document, container, false)

        var i1 = Internet()

        if(i1.checkConnection(requireContext()))
        {
            var args = RecruitmentApplicantDocumentFragmentArgs.fromBundle(requireArguments())
            setupWebViewWithUrl(binding.pdfViewer, args.resumeLink)
        }
        else
        {
            Toast.makeText(requireContext(),"Please Check internet connection!!", Toast.LENGTH_LONG).show()
        }

        return binding.root

    }

    private fun setupWebViewWithUrl(webView: WebView?, url: String) {
        webView?.let {
            // Enable JavaScript in the WebView
            it.settings.javaScriptEnabled = true
            it.settings.loadWithOverviewMode = true
            it.settings.useWideViewPort = true

            // Configure a WebViewClient to handle navigation events
            it.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    // Return false to allow the WebView to handle the URL
                    return false
                }
            }

            // Configure a WebChromeClient (optional)
            it.webChromeClient = object : WebChromeClient() {}

            // Generate HTML content to embed the PDF
            val htmlContent = getPDFHtml(url)

            Toast.makeText(requireContext(),htmlContent,Toast.LENGTH_LONG).show()
            // Load the HTML content into the WebView
            it.loadData(htmlContent, "text/html", "utf-8")
        }
    }

    // This function generates the HTML content to embed the PDF.
    private fun getPDFHtml(url: String): String {
        return """
            <!DOCTYPE html> 
            <html> 
            <head> 
                <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"> 
                <style> 
                    body, html { 
                        margin: 0; 
                        height: 100%; 
                        overflow: hidden; 
                    } 
                    iframe { 
                        position: absolute; 
                        top: 0; 
                        left: 0; 
                        width: 100%; 
                        height: 100%; 
                        border: none; 
                    } 
                </style> 
            </head> 
            <body> 
                <iframe src="$url" allow="autoplay"></iframe> 
            </body> 
            </html> 
        """
    }

}