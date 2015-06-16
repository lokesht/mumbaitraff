package in.lastlocal.twitter;

/**
 * Created by Lokesh on 14-06-2015.
 */

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;
import com.twitter.sdk.android.tweetui.UserTimeline;

import com.twitter.sdk.android.core.Callback;

import in.lastlocal.constant.AppConstant;
import in.lastlocal.mumbaitraffic.R;
import io.fabric.sdk.android.Fabric;

public class TimelineActivity extends AppCompatActivity
{
    final String CONSUMER_KEY = AppConstant.CONSUMER_KEY;
    final String CONSUMER_SECRET_KEY = AppConstant.CONSUMER_SECRET_KEY;

    TweetTimelineListAdapter adapter;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        initialize();
        authenticate();
        timeLineTweet();

        swipeToRefresh();

        //searchTimeLine();
    }

    public void initialize() {
        mToolbar = (Toolbar) findViewById(R.id.inc_tool_bar);
        setSupportActionBar(mToolbar);
    }

    /**
     * Necessory method
     */
    private void authenticate() {
        TwitterAuthConfig authConfig =
                new TwitterAuthConfig(CONSUMER_KEY, CONSUMER_SECRET_KEY);

        Fabric.with(this, new TwitterCore(authConfig), new TweetUi());
    }

    private void timeLineTweet() {
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName("smart_mumbaikar")
                .build();
        if (userTimeline != null) {
            adapter = new TweetTimelineListAdapter(this, userTimeline);
            ListView listViewTweet = (ListView) findViewById(R.id.lv_list);
            listViewTweet.setAdapter(adapter);
        }
    }

    private void swipeToRefresh() {

        // inside onCreate
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_tweet_layout);

        //final SearchTimeline userTimeline = new SearchTimeline.Builder().query("#twitter").build();
        // final UserTimeline userTimeline = new UserTimeline.Builder().screenName("smart_mumbaikar").build();
        // final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(this, userTimeline);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //swipeLayout.setRefreshing(true);
                //  swipeLayout.setRefreshing(false);
                adapter.refresh(new Callback() {

                    @Override
                    public void success(Result result) {
                        Toast.makeText(TimelineActivity.this, "success", Toast.LENGTH_SHORT).show();
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Toast.makeText(TimelineActivity.this, exception.toString() + "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
//
//    public void searchTimeLine()
//    {
//        // inside onCreate
//        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_tweet_layout);
//        final SearchTimeline timeline = new SearchTimeline.Builder().query("#twitter").build();
//        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(this, timeline);
//        setListAdapter(adapter);
//
//        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeLayout.setRefreshing(true);
//                adapter.refresh(new Callback() {
//                    @Override
//                    public void success(Result result) {
//                        swipeLayout.setRefreshing(false);
//                    }
//
//                    @Override
//                    public void failure(TwitterException exception) {
//                        // Toast or some other action
//                    }
//                });
//            }
//        });
//    }
}