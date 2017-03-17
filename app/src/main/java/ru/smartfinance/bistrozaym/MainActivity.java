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
    final private int REQUEST_READ_SMS = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreloader = (ProgressBar) findViewById(R.id.preloader);
        mWebView = (WebView) findViewById(R.id.web_view_main);
        WebViewClient client = new CustomWebViewClient();
        WebChromeClient webChromeClient = new CustomWebChromeClient();
        mWebView.setWebViewClient(client);
        mWebView.setWebChromeClient(webChromeClient);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(getResources().getString(R.string.main_url));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.GET_ACCOUNTS},
                    REQUEST_GET_ACCOUNTS);
        }

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

        Log.i("phone", "dfgfgdfg");
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String mPhoneNumber = tMgr.getLine1Number();
                    Log.i("phone", "phone: " + mPhoneNumber);
                }
                return;
            case REQUEST_GET_ACCOUNTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("accounts", "sfdgdsg");
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
}
