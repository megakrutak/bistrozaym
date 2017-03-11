package ru.smartfinance.dengoplat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mPreloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreloader = (ProgressBar) findViewById(R.id.preloader);
        mWebView = (WebView) findViewById(R.id.web_view_main);
        WebViewClient client = new DengoplatWebViewClient();
        WebChromeClient webChromeClient = new DengoplatWebChromeClient();
        mWebView.setWebViewClient(client);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(getResources().getString(R.string.main_url));
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
