package in.lastlocal.mumbaitraffic;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import in.lastlocal.map.MapTest;
import in.lastlocal.map.MapsActivity;
import in.lastlocal.twitter.SingleTweet;
import in.lastlocal.twitter.TimelineActivity;
import in.lastlocal.twitter.TweetListActivity;
import io.fabric.sdk.android.Fabric;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.LoadCallback;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;


public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void onMapNearBy(View v) {
        startActivity(new Intent(this, MapsActivity.class));
    }

    public void onNearByPolice(View v)
    {
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=15&q=near+by+police+station");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void onMapTest(View v) {
        startActivity(new Intent(this, MapTest.class));
    }

    public void onTraffic(View v) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?z=15&q=traffic+on+map");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
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
