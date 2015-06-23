package in.lastlocal.twitter;

/**
 * Created by Lokesh on 14-06-2015.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetUi;
import com.twitter.sdk.android.tweetui.UserTimeline;

import in.lastlocal.constant.AppConstant;
import in.lastlocal.mumbaitraffic.R;
import io.fabric.sdk.android.Fabric;
import tr.xip.errorview.ErrorView;

public class TimelineActivity extends AppCompatActivity {
    final String CONSUMER_KEY = AppConstant.CONSUMER_KEY;
    final String CONSUMER_SECRET_KEY = AppConstant.CONSUMER_SECRET_KEY;

    TweetTimelineListAdapter adapter;
    SwipeRefreshLayout swipeLayout;
    ListView listViewTweet;
    Toolbar mToolbar;

    ErrorView mErrorView;

    // Progress Dialog
    private ProgressDialog pDialog;

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        initialize();
    }

    public void initialize() {
        mToolbar = (Toolbar) findViewById(R.id.inc_tool_bar);
        setSupportActionBar(mToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mErrorView = (ErrorView) findViewById(R.id.error_view);
        mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
            @Override
            public void onRetry() {
                loadTweet();
            }
        });

        loadTweet();
    }

    private void loadTweet() {
        if (isConnectionAvailable()) {
            mErrorView.setVisibility(View.GONE);
            new DownloadLoadTweet().execute("");
        } else {
            mErrorView.setVisibility(View.VISIBLE);
        }
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

    private boolean isConnectionAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /**
     * Background Async Task to download file
     */
    class DownloadLoadTweet extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread
         * Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(progress_bar_type);
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {

            String err = "F";
            int count;
            try {

                /** Some Error Ocured*/
                authenticate();
                timeLineTweet();

                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                    err = "T";
                    e.printStackTrace();
                }

            } catch (Exception e) {
                err = "T";
                Log.e("Error: ", e.getMessage());
            }
            return err;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
            pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task
         * Dismiss the progress dialog
         * *
         */
        @Override
        protected void onPostExecute(String err) {
            // dismiss the dialog after the file was downloaded
            dismissDialog(progress_bar_type);
            if(err.equalsIgnoreCase("T"))
            {
                mErrorView.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * Showing Dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case progress_bar_type: // we set this to 0
                pDialog = new ProgressDialog(this);
                pDialog.setMessage("Loading wait...");
                pDialog.setIndeterminate(true);

                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setCancelable(true);
                pDialog.show();
                return pDialog;
            default:
                return null;
        }
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

