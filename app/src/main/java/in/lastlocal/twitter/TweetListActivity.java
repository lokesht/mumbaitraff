package in.lastlocal.twitter;

/**
 * Created by USER on 13-Jun-15.
 */
import android.app.ListActivity;
import android.os.Bundle;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.tweetui.LoadCallback;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.TweetViewFetchAdapter;
import java.util.Arrays;
import java.util.List;

import in.lastlocal.mumbaitraffic.R;

public class TweetListActivity extends ListActivity {

    List<Long> tweetIds = Arrays.asList(503435417459249153L,
            510908133917487104L,
            473514864153870337L,
            477788140900347904L);

    final TweetViewFetchAdapter adapter =
            new TweetViewFetchAdapter<CompactTweetView>(
                    TweetListActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tweet_list);
        setListAdapter(adapter);
        adapter.setTweetIds(tweetIds,
                new LoadCallback<List<Tweet>>() {
                    @Override
                    public void success(List<Tweet> tweets) {
                        // my custom actions
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Toast.makeText(...).show();
                    }
                });
    }
}