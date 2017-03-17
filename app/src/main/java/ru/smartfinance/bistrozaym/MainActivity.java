package ru.smartfinance.bistrozaym;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private ProgressBar mPreloader;
    final private int REQUEST_READ_PHONE_STATE = 1;
    final private int REQUEST_GET_ACCOUNTS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreloader = (ProgressBar) findViewById(R.id.preloader);
        initWebView(getResources().getString(R.string.main_url));
        checkPermissions();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String mPhoneNumber = tMgr.getLine1Number();
                }
                return;
            case REQUEST_GET_ACCOUNTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AccountManager am = AccountManager.get(this);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    Account[] acc = am.getAccounts();

                    for (Account ac: acc) {
                        Log.i("accounts", ac.toString());
                    }
                }
        }
    }

    private void checkPermissions() {
        /*int checkPermissionReadPhoneState
                = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (checkPermissionReadPhoneState != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        }*/

        int checkPermissionGetAccounts
                = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);

        if (checkPermissionGetAccounts != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    REQUEST_GET_ACCOUNTS);
        }
    }

    private void initWebView(String url) {
        mWebView = (WebView) findViewById(R.id.main_web_view);
        WebViewClient client = new MainWebViewClient();
        WebChromeClient webChromeClient = new MainWebChromeClient();
        mWebView.setWebViewClient(client);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
    }
}
