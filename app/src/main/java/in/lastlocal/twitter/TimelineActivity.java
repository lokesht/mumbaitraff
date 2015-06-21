package in.lastlocal.twitter;

/**
 * Created by Lokesh on 14-06-2015.
 */

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
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

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import in.lastlocal.constant.AppConstant;

import in.lastlocal.mumbaitraffic.R;
import io.fabric.sdk.android.Fabric;

public class TimelineActivity extends AppCompatActivity
{
    final String CONSUMER_KEY = AppConstant.CONSUMER_KEY;
    final String CONSUMER_SECRET_KEY = AppConstant.CONSUMER_SECRET_KEY;

    TweetTimelineListAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    ListView listViewTweet;
    Toolbar mToolbar;

    // Progress Dialog
    private ProgressDialog pDialog;
    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        initialize();

        authenticate();

        new DownloadLoadTweet().execute("");

        // timeLineTweet();

        //swipeToRefresh();
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

    private void timeLineTweet(UserTimeline userTimeline) {

        if (userTimeline != null) {
            swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_tweet_layout);
            listViewTweet = (ListView) findViewById(R.id.lv_list);

            adapter = new TweetTimelineListAdapter(this, userTimeline);
            listViewTweet.setAdapter(adapter);

            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override
                public void onRefresh() {
                    swipeLayout.setRefreshing(true);
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
                            swipeLayout.setRefreshing(false);
                        }
                    });
                }
            });
        }
    }

    //private void swipeToRefresh() {

        //final SearchTimeline userTimeline = new SearchTimeline.Builder().query("#twitter").build();
        // final UserTimeline userTimeline = new UserTimeline.Builder().screenName("smart_mumbaikar").build();
        // final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(this, userTimeline);




    //}
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

    /**
     * Background Async Task to download file
     * */
    class DownloadLoadTweet extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         * */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {

                UserTimeline userTimeline = new UserTimeline.Builder()
                        .screenName("smart_mumbaikar")
                        .build();
                timeLineTweet(userTimeline);
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        /**
         * Updating progress bar
         * */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);
        }

    }
    /**
     * Showing Dialog
     * */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Downloading file. Please wait...");
                pDialog.setIndeterminate(true);
                pDialog.setMax(100);
                pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
    }
}