package ru.smartfinance.bistrozaym;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

class MainWebViewClient extends WebViewClient {

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        ProgressBar preloader = (ProgressBar) ((Activity) view.getContext()).findViewById(R.id.preloader);
        preloader.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {

        if (!url.contains("bistro-zaym.ru")) {
            Activity mainActivity = (Activity) webView.getContext();

            Intent intent = new Intent(mainActivity, PartnerActivity.class);
            intent.putExtra(PartnerActivity.EXTRA_URL, url);
            mainActivity.startActivity(intent);
            return true;
        }

        return false;
        // все остальные ссылки будут спрашивать какой браузер открывать
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        //webView.getContext().startActivity(intent);
        //return true;
    }
}
