package in.lastlocal.mumbaitraffic;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;


import io.fabric.sdk.android.Fabric;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.LoadCallback;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;


public class MainActivity extends ActionBarActivity {

    final String CONSUMER_KEY = "iAavzdUEc98rrid9qadJMfIiJ";
    final String CONSUMER_SECRET_KEY ="bqYrsHZJj6289VPJMTChooyGL6BUPveTClEN0JZKKFL5Qomgov";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        TwitterAuthConfig authConfig =
                new TwitterAuthConfig(CONSUMER_KEY,
                        CONSUMER_SECRET_KEY);
        //Fabric.with(this, new Twitter(authConfig));

        Fabric.with(this, new TwitterCore(authConfig), new TweetUi());
        setContentView(R.layout.activity_main);

    }

    public void singleTweet()
    {
        final LinearLayout myLayout
                = (LinearLayout) findViewById(R.id.bike_tweet);

        final long tweetId = 510908133917487104L;
        TweetUtils.loadTweet(tweetId, new LoadCallback<Tweet>() {
            @Override
            public void success(Tweet tweet) {
                myLayout.addView(new TweetView(MainActivity.this, tweet));
            }

            @Override
            public void failure(TwitterException exception) {
                // Toast.makeText(...).show();
            }
        });
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
           startActivity(new  Intent(this,TweetListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
