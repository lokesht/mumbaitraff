package in.lastlocal.map;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import in.lastlocal.mumbaitraffic.R;

public class WebViewNearByPolice extends ActionBarActivity {

    private boolean loadingFinished = true;
    private boolean redirect = false;
    private WebView webView;
    private static  int i = 0;

    private String mapPath = "https://maps.google.com/?q=near by police station&ll=19.0823319,72.8671099&t=m&z=12&layer=t";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_near_by_police);

        webView = (WebView) findViewById(R.id.mapview);
        startWebView(mapPath);
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
                if (progressDialog == null && i==0) {
                    // in standard case YourActivity.this
                    i++;
                    progressDialog = new ProgressDialog(WebViewNearByPolice.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                try {
                    if(!redirect){
                        loadingFinished = true;
                    }

                    if(loadingFinished && !redirect){
                        progressDialog.dismiss();
                        progressDialog = null;
                    } else{
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

    // Open previous opened link from history on webview when back button pressed
    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }
}