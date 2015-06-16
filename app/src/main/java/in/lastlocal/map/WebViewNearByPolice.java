package in.lastlocal.map;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import in.lastlocal.mumbaitraffic.R;

public class WebViewNearByPolice extends ActionBarActivity {

    WebView myWebView;
    String mapPath = "https://maps.google.com/?q=near by police station&ll=19.0823319,72.8671099&t=m&z=11&layer=t";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_near_by_police);
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
