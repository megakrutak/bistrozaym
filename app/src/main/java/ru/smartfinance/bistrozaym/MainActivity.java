package ru.smartfinance.bistrozaym;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.Preference;
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

import com.google.firebase.analytics.FirebaseAnalytics;
import com.yandex.metrica.YandexMetrica;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    final private int REQUEST_READ_PHONE_STATE = 1;
    final private int REQUEST_GET_ACCOUNTS = 2;
    final private String APP_PREFERENCES = "bistrozaym_preferences";
    final private String APP_PREFERENCES_ACCOUNTS_RECEIVED = "accounts_received";

    private Helper helper;
    private SharedPreferences mPreferences;
    private boolean mAccountsReceived = false;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWebView(getResources().getString(R.string.main_url));
        helper = BistrozaymApp.getComponent().getHelper();
        mPreferences = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (mPreferences.contains(APP_PREFERENCES_ACCOUNTS_RECEIVED)) {
            mAccountsReceived = mPreferences.getBoolean(APP_PREFERENCES_ACCOUNTS_RECEIVED, false);
        }

        if (!mAccountsReceived) {
            checkPermissionsForAccounts();
        }

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
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
    protected void onStop() {
        super.onStop();

        SharedPreferences.Editor editor = mPreferences.edit();
        if (mAccountsReceived) {
            editor.putBoolean(APP_PREFERENCES_ACCOUNTS_RECEIVED, true);
            editor.apply();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_GET_ACCOUNTS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    AccountManager am = AccountManager.get(this);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    for (Account account: am.getAccounts()) {
                        JSONObject accountInfo = new JSONObject();
                        if (helper.isPhone(account.name) || helper.isEmail(account.name)) {
                            try {
                                accountInfo.put("account_name", account.name + "|" + account.type);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        YandexMetrica.reportEvent("accounts", accountInfo.toString());
                        mAccountsReceived = true;
                    }
                }
        }
    }

    private void checkPermissionsForAccounts() {
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
        mWebView.addJavascriptInterface(new MainJavaScriptInterface(), "HtmlHandler");
        mWebView.loadUrl(url);
    }
}
