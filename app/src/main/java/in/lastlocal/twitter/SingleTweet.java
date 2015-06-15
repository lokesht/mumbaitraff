package in.lastlocal.twitter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.LoadCallback;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.twitter.sdk.android.tweetui.TweetView;

import in.lastlocal.constant.AppConstant;
import in.lastlocal.mumbaitraffic.R;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Lokesh on 14-06-2015.
 */
public class SingleTweet extends ActionBarActivity {

    final String CONSUMER_KEY = AppConstant.CONSUMER_KEY;
    final String CONSUMER_SECRET_KEY =AppConstant.CONSUMER_SECRET_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_single_tweet);
authenticate();

    }

    public void authenticate()
    {
        TwitterAuthConfig authConfig =
                new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET_KEY);
        //Fabric.with(this, new Twitter(authConfig));

        Fabric.with(this, new TwitterCore(authConfig), new TweetUi());
    }


    public void singleTweet()
    {
        final LinearLayout myLayout
                = (LinearLayout) findViewById(R.id.bike_tweet);

        final long tweetId = 510908133917487104L;
        TweetUtils.loadTweet(tweetId, new LoadCallback<Tweet>() {
            @Override
            public void success(Tweet tweet) {
                myLayout.addView(new TweetView(SingleTweet.this, tweet));
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
            startActivity(new Intent(this,TweetListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

