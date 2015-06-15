package in.lastlocal.map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import in.lastlocal.mumbaitraffic.R;

public class MapsActivity extends FragmentActivity {


    static final LatLng MUMBAI = new LatLng(18.975, 72.825);
    static final LatLng KIEL = new LatLng(53.551, 9.993);

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setUpMapIfNeeded();

        initialize();
    }

    public void initialize()
    {
        FragmentActivity f =new FragmentActivity();
        mToolbar = (Toolbar)findViewById(R.id.inc_tool_bar);
        AppCompatActivity appCompatActivity = (AppCompatActivity)(FragmentActivity)this;
        appCompatActivity.setSupportActionBar(mToolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
       // mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));

        //   Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
        //          .title("Hamburg"));

//            Marker kiel = map.addMarker(new MarkerOptions()
//                    .position(KIEL)
//                    .title("Kiel")
//                    .snippet("Kiel is cool")
//                    .icon(BitmapDescriptorFactory
//                            .fromResource(R.mipmap.ic_launcher)));

        UiSettings mUiSettings = mMap.getUiSettings();
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setMapToolbarEnabled(true);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(MUMBAI, 12));
        mMap.setTrafficEnabled(true);
        mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);

        //mMap.setPadding(0,0,50,0);

       // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        // Zoom in, animating the camera.
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    public void onZoomIn(View v)
    {
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    public void onZoomOut(View v)
    {
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
    }

    public void onTilt3D(View v)
    {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(MUMBAI) // Sets the center of the map to
                        // Golden Gate Bridge
                .zoom(mMap.getCameraPosition().zoom)                   // Sets the zoom
               // .bearing(90) // Sets the orientation of the camera to east
                .tilt(30)    // Sets the tilt of the camera to 30 degrees
                .build();    // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
