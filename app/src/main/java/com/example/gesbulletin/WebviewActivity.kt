package com.example.gesbulletin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class WebviewActivity : AppCompatActivity() {

    private lateinit var wb_webView : WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewSetup(){

        wb_webView.webViewClient = WebViewClient()

        wb_webView.apply {
            loadUrl("https://ges-coengg.org/")
            settings.javaScriptEnabled = true
        }
    }

    override fun onBackPressed() {
        if (wb_webView.canGoBack()) wb_webView.goBack() else super.onBackPressed()
    }
}