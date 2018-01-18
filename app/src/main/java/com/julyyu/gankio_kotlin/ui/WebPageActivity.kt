package com.julyyu.gankio_kotlin.ui

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.webkit.WebView
import kotterknife.bindView

import com.julyyu.gankio_kotlin.R
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.widget.ProgressBar




class WebPageActivity : AppCompatActivity() {

    val webView : WebView by bindView(R.id.web_view)
//    var sonicSession: SonicSession? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED)


        val url = intent.getStringExtra("url")
        val des = intent.getStringExtra("des")

//        // step 1: Initialize sonic engine if necessary, or maybe u can do this when application created
//        if (!SonicEngine.isGetInstanceAllowed()) {
//            SonicEngine.createInstance(SonicRuntimeImpl(application), SonicConfig.Builder().build())
//        }
//
//        var sonicSessionClient: SonicSessionClientImpl? = null
//        // step 2: Create SonicSession
//        sonicSession = SonicEngine.getInstance().createSession(url, SonicSessionConfig.Builder().build())
//        if (null != sonicSession) {
//            sonicSessionClient = SonicSessionClientImpl()
//            sonicSession!!.bindClient(sonicSessionClient)
//        } else {
//            // this only happen when a same sonic session is already running,
//            // u can comment following codes to feedback as a default mode.
//            throw UnknownError("create session fail!")
//        }

        // step 3: BindWebView for sessionClient and bindClient for SonicSession
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

//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//                if (sonicSession != null) {
//                    sonicSession!!.getSessionClient().pageFinish(url)
//                }
//            }

//            @TargetApi(21)
//            override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {
//                return shouldInterceptRequest(view, request.url.toString())
//            }
//
//            override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
//                if (sonicSession != null) {
//                    return sonicSession!!.getSessionClient().requestResource(url) as WebResourceResponse
//                }
//                return null
//            }
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


        val webSettings = webView.settings

        // step 4: bind javascript
        // note:if api level lower than 17(android 4.2), addJavascriptInterface has security
        // issue, please use x5 or see https://developer.android.com/reference/android/webkit/
        // WebView.html#addJavascriptInterface(java.lang.Object, java.lang.String)
        webSettings.javaScriptEnabled = true
//        webView.removeJavascriptInterface("searchBoxJavaBridge_")
//        intent.putExtra(SonicJavaScriptInterface.PARAM_LOAD_URL_TIME, System.currentTimeMillis())
//        webView.addJavascriptInterface(SonicJavaScriptInterface(sonicSessionClient, intent), "sonic")

        // init webview settings
        webSettings.allowContentAccess = true
        webSettings.databaseEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.setAppCacheEnabled(true)
        webSettings.savePassword = false
        webSettings.saveFormData = false
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true

        // step 5: webview is ready now, just tell session client to bind
//        if (sonicSessionClient != null) {
//            sonicSessionClient.bindWebView(webView)
//            sonicSessionClient.clientReady()
//        } else { // default mode
//            webView.loadUrl(url)
//        }
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

//    override fun onDestroy() {
//        if (null != sonicSession) {
//            sonicSession!!.destroy()
//            sonicSession = null
//        }
//        super.onDestroy()
//    }


}
