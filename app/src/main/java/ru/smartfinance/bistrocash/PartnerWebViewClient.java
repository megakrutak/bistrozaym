package ru.smartfinance.bistrocash;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.net.URISyntaxException;

class PartnerWebViewClient extends WebViewClient {

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        ProgressBar preloader = (ProgressBar) ((Activity) view.getContext()).findViewById(R.id.preloader_partner);
        preloader.setVisibility(View.INVISIBLE);
        setPartnerTitle(view.getContext(), url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return false;
    }

    private void setPartnerTitle(Context context, String url) {
        Helper helper = new Helper(context);
        String partnerName;
        try {
            partnerName = helper.getPartnerNameByUrl(url);
        } catch (URISyntaxException e) {
            partnerName = url;
        }


        ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(partnerName);
        }
    }
}
