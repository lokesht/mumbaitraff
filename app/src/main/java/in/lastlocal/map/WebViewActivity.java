package in.lastlocal.map;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import in.lastlocal.mumbaitraffic.R;

public class WebViewActivity extends Activity {

    WebView myWebView;

    String mapPath = "https://maps.google.com/?ll=19.0823319,72.8671099&t=m&z=11&layer=t";
    // String mapPath ="https://www.google.co.in/maps/@19.0823319,72.8671099,13z/data=!5m1!1e1?hl=en";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        myWebView = (WebView) findViewById(R.id.mapview);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());

        myWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        myWebView.loadUrl(mapPath);
    }

}