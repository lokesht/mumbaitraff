package in.lastlocal.mumbaitraffic;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import in.lastlocal.information.MainInformation;
import in.lastlocal.map.MapsActivity;
import in.lastlocal.map.WebViewActivity;
import in.lastlocal.twitter.SingleTweet;
import in.lastlocal.twitter.TimelineActivity;
import in.lastlocal.twitter.TweetListActivity;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    public static final String NEAR_BY_POLICE = "NEAR_BY_PLOICE";
    public static final String TRAFFIC = "TRAFFIC";
    public static final String EXTRA_ITEM = "Extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        //initialise();
    }

    public void initialise() {

    }

    /** */
    public void onWebViewTraffic(View v) {
        Intent in = new Intent(this, WebViewActivity.class);
        startActivity(in);
    }

    /** */
    public void onTimeLineTweet(View v) {
        startActivity(new Intent(this, TimelineActivity.class));
    }

    public void onInformation(View v) {
        Intent in = new Intent(this, MainInformation.class);
        startActivity(in);
    }

    /** */
    public void onEmergency(View v) {
       // Intent in = new Intent()
    }

    public void onEmergencySetting(View v) {
        Intent in = new Intent(this, EmergencySMS.class);
        startActivity(in);
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

    public void onSingleTweet(View v) {
        startActivity(new Intent(this, SingleTweet.class));
    }

    public void onListTweetId(View v) {
        startActivity(new Intent(this, TweetListActivity.class));
    }


    public void maps(View view) {

        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=" + "mumbai"));
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void navigation(View view) {

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
