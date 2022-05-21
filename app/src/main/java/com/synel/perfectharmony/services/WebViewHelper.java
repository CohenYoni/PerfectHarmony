package com.synel.perfectharmony.services;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Helper class of actions that required WebView component.
 */
public class WebViewHelper {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.67 Safari/537.36";

    private final WebView helperWebView;

    public WebViewHelper(Context context) {

        this.helperWebView = new WebView(context);
        setUpWebView();
    }

    /**
     * Set up WebView to act like a desktop app in background.
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void setUpWebView() {

        helperWebView.setVisibility(View.GONE);
        helperWebView.getSettings().setUserAgentString(USER_AGENT);
        helperWebView.getSettings().setUseWideViewPort(true);  // for desktop visualisation
        helperWebView.getSettings().setLoadWithOverviewMode(true);  // for desktop visualisation
        helperWebView.getSettings().setJavaScriptEnabled(true);
        helperWebView.getSettings().setDomStorageEnabled(true);
        helperWebView.setWebViewClient(new WebViewClient());
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();
    }

    /**
     * Load an URL at background (in order to receive website cookies, etc.).
     */
    public void loadUrlAtBackground(String url) {

        helperWebView.loadUrl(url);
    }

    /**
     * Get saved cookies of a website.
     */
    public String getCookiesOfWebSite(String url) {

        return CookieManager.getInstance().getCookie(url);
    }
}
