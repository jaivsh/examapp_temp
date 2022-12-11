package com.app.examprep.fragment.home.course

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.provider.SyncStateContract
import android.view.View
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.app.examprep.R
import com.app.examprep.databinding.FragmentLessonsBinding
import com.app.examprep.databinding.FragmentPrimaryBinding
import com.app.examprep.others.Constants
import java.net.URLEncoder

@Suppress("DEPRECATION")
class PdfViewFragment : Fragment(R.layout.fragment_primary) {


    private lateinit var binding : FragmentPrimaryBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPrimaryBinding.bind(view)

       binding.webview.webViewClient  = object : WebViewClient(){

           override fun onReceivedError(
               view: WebView?,
               request: WebResourceRequest?,
               error: WebResourceError?
           ) {
               super.onReceivedError(view, request, error)

            //   Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()

           }

           override fun onPageFinished(view: WebView?, url: String?) {
               super.onPageFinished(view, url)

               binding.progressbar.visibility = View.INVISIBLE
           }
       }


        binding.webview.settings.setSupportZoom(true)
        binding.webview.settings.javaScriptEnabled = true
      val url = Uri.parse(Constants.curUrl)
        val url2 = URLEncoder.encode(url.toString(), "ISO-8859-1")
        binding.webview.loadUrl("https://docs.google.com/gview?embedded=true&url=$url2")


    }
}
