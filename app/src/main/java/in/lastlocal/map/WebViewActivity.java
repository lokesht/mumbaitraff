package in.lastlocal.map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import in.lastlocal.mumbaitraffic.R;

public class WebViewActivity extends Activity {

    private boolean loadingFinished = true;
    private boolean redirect = false;
    private WebView webView;
    private static int in = 0;

    String mapPath = "https://maps.google.com/?ll=19.0823319,72.8671099&t=m&z=12&layer=t";
    //String mapPath = "https://maps.google.com/";
    // String mapPath ="https://www.google.co.in/maps/@19.0823319,72.8671099,13z/data=!5m1!1e1?hl=en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = (WebView) findViewById(R.id.mapview);
        startWebView(mapPath);

        /** reset */
         in = 0;
    }

    private boolean isConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }
    /** */
    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!loadingFinished) {
                    redirect = true;
                }
                loadingFinished = false;
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingFinished = false;
            }

            //Show loader on url load
            public void onLoadResource(WebView view, String url) {
                if (progressDialog == null && in == 0) {

                    View v = findViewById(R.id.ll_color_view);
                    v.setVisibility(View.VISIBLE);

                    // in standard case YourActivity.this
                    in++;
                    progressDialog = new ProgressDialog(WebViewActivity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }


            public void onPageFinished(WebView view, String url) {
                try {
                    if (!redirect) {
                        loadingFinished = true;
                    }

                    if (loadingFinished && !redirect) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    } else {
                        redirect = false;
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }

        });

        // Javascript enabled on webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });
        webView.loadUrl(url);
    }

}