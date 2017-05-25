package com.julyyu.gankio_kotlin.ui

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.webkit.WebView
import butterknife.bindView

import com.julyyu.gankio_kotlin.R
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.widget.ProgressBar
import android.widget.TextView


class WebPageActivity : AppCompatActivity() {

    val webView : WebView by bindView(R.id.web_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            this.finish()
        }
        val progressbar = findViewById(R.id.progressbar_webview) as ProgressBar
        val swipeRefresh = findViewById(R.id.swipelayout) as SwipeRefreshLayout
        webView.settings.javaScriptEnabled = true
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                return true
            }
        })
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress >= 100) {
                    progressbar.setVisibility(View.GONE)
                    swipeRefresh.isRefreshing = false
                } else {
                    swipeRefresh.isRefreshing = true
                    progressbar.setProgress(newProgress)
                    progressbar.setVisibility(View.VISIBLE)
                }
            }
        })

        val url = intent.getStringExtra("url")
        val des = intent.getStringExtra("des")
        webView.loadUrl(url)
        supportActionBar!!.title = des
        swipeRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                webView.loadUrl(url)
            }
        })
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }

}
