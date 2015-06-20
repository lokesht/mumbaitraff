package in.lastlocal.widget_sos;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import in.lastlocal.constant.AppConstant;
import in.lastlocal.mumbaitraffic.EmergencySMS;

public class SendSmsActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    /** */
    SharedPreferences sharedpreferences;
    protected static final String TAG = "SendSmsActivity";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    String lat = "";
    String lon = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        buildGoogleApiClient();

        /** */
        sharedpreferences = getSharedPreferences(AppConstant.CONTACT_PREFERENCE, Context.MODE_PRIVATE);
    }

    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = String.valueOf(mLastLocation.getLatitude());
            lon = String.valueOf(mLastLocation.getLongitude());

            SharedPreferences.Editor edit = sharedpreferences.edit();
            edit.putString(5+"",lat+"#"+ lon);
            edit.apply();

            onSendMessage(null);
        } else {
            _getLocation();
            onSendMessage(null);
        }
    }

    private void _getLocation() {
        // Get the location manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        Criteria criteria = new Criteria();
//        String bestProvider = locationManager.getBestProvider(criteria, false);
//        Location location = locationManager.getLastKnownLocation(bestProvider);

        String locationProvider = LocationManager.NETWORK_PROVIDER;
       // Or use LocationManager.GPS_PROVIDER

        Location location = locationManager.getLastKnownLocation(locationProvider);

        try {
            lat = location.getLatitude() + "";
            lon = location.getLongitude() + "";

        } catch (NullPointerException e) {

            String ll=sharedpreferences.getString("5","18.9750#72.8258");

            lat = ll.split("#")[0] + "";
            lon = ll.split("#")[1] + "";
        }
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());

        onSendMessage(null);
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }

    public void onSendMessage(View v) {

        String con1, conName1, con2, conName2, con3, conName3;
        String[] t1;
        con1 = sharedpreferences.getString("2", "");
        con2 = sharedpreferences.getString("3", "");
        con3 = sharedpreferences.getString("4", "");

        if (con1.length() == 0 && con2.length() == 0 && con3.length() == 0) {
            Toast.makeText(this, "No contact saved", Toast.LENGTH_SHORT).show();
            return;
        }

        if (con1.length() > 10) {
            t1 = con1.split("#");
            con1 = t1[0];
            conName1 = t1[1];

            if (con1.length() > 9) {
                send(con1, conName1 + " message ");
            }
        }

        if (con2.length() > 10) {
            t1 = con2.split("#");
            con2 = t1[0];
            conName2 = t1[1];
            if (con2.length() > 9) {
                send(con2, conName2 + " message ");
            }
        }

        if (con3.length() > 10) {
            t1 = con3.split("#");
            con3 = t1[0];
            conName3 = t1[1];
            if (con3.length() > 9) {
                send(con3, conName3 + " message ");
            }
        }


        intentToLoc();
    }

    private void send(String contact, String msg) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(contact, null, msg +"I am at approx http://maps.google.com/?q="+lat+","+ lon, null, null);

        Toast.makeText(this, msg + "I am at approx http://maps.google.com/?q="+lat +","+ lon, Toast.LENGTH_SHORT).show();
    }

    public void intentToLoc() {
        Intent in = new Intent(this, EmergencySMS.class);
        startActivity(in);
    }
}
