package in.lastlocal.mumbaitraffic;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        new AsyncTask<Void, Integer, String>() {

            protected void onPreExecute() {

            }

            ;

            @Override
            protected String doInBackground(Void... params) {

                /** Copy full database from asset Folder to database Folder */
                copyDatabaseFromAsset();

                return "";
            }

            protected void onPostExecute(String result) {
                if (AppConstant.DEBUG)
                    Toast.makeText(SplashActivity.this, "success", Toast.LENGTH_SHORT).show();

				/**/
//                Intent in = new Intent(ActivitySplash.this, ActivityMain.class);
//                startActivity(in);
//                finish();
            }

        }.execute();

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

    /**
     * Copy Database from asset Folder to data directory
     */
    public void copyDatabaseFromAsset() {

		/* Insert Database */
        DBHelper db = new DBHelper(this);
        try {
            boolean dbExist = db.isDataBaseAvailable();

            if (!dbExist)
                db.copyDataBaseFromAsset();

        } catch (Exception e) {
            //AppLogger.writeLog("state " + TAG + " -- " + e.toString());
            Log.e("", e.toString());
        }
        //System.out.println(t.getTime(t));
        // }
    }

}
