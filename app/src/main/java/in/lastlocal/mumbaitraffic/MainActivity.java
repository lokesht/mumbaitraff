package in.lastlocal.mumbaitraffic;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import in.lastlocal.map.MapsActivity;
import in.lastlocal.map.WebViewActivity;
import in.lastlocal.twitter.SingleTweet;
import in.lastlocal.twitter.TimelineActivity;
import in.lastlocal.twitter.TweetListActivity;


public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialise();
    }

    public void initialise() {
        mToolbar = (Toolbar) findViewById(R.id.inc_tool_bar);
        setSupportActionBar(mToolbar);
    }

    public void onSingleTweet(View v) {
        startActivity(new Intent(this, SingleTweet.class));
    }

    public void onListTweetId(View v) {
        startActivity(new Intent(this, TweetListActivity.class));
    }

    public void onTimeLineTweet(View v) {
        startActivity(new Intent(this, TimelineActivity.class));
    }

    public void onMapInside(View v) {
        startActivity(new Intent(this, MapsActivity.class));
    }

    public void onNearByPolice(View v) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=15&q=near+by+police+station");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void onTraffic(View v) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=15&q=traffic+on+map");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void onWebView(View v)
    {
        startActivity(new Intent(this, WebViewActivity.class));
    }

    public  void maps(View view) {

        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=" + "mumbai"));
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public  void navigation(View view) {

        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + "mumbai"));
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
