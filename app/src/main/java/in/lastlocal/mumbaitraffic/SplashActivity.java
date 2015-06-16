package in.lastlocal.mumbaitraffic;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import in.lastlocal.constant.AppConstant;


public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler mHandler;
    private Runnable mRunnable;

    /** */
    TextView textViewMarathi;
    TextView textViewEnglish;

    private static final long SPLASH_DURATION = 3500L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initialise();
    }

    public void initialise() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        };

        textViewEnglish = (TextView) findViewById(R.id.tv_english);
        textViewMarathi = (TextView) findViewById(R.id.tv_marathi);

        textViewEnglish.setOnClickListener(this);
        textViewMarathi.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // mHandler.postDelayed(mRunnable, SPLASH_DURATION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent in;
        switch (v.getId()) {
            case R.id.tv_english:
                in = new Intent(this, MainActivity.class);
                startActivity(in);
                break;

            case R.id.tv_marathi:
                AppConstant.isMarathi = true;
                in = new Intent(this, MainActivity.class);
                startActivity(in);

                break;

        }
    }
}
