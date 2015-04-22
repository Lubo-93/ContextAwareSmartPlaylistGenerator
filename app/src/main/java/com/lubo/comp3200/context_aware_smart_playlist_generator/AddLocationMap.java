package com.lubo.comp3200.context_aware_smart_playlist_generator;

import android.app.FragmentTransaction;
import android.location.*;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class AddLocationMap extends ActionBarActivity implements OnMapReadyCallback,
                                                  GoogleApiClient.ConnectionCallbacks,
                                                  GoogleApiClient.OnConnectionFailedListener{

    // Map fragment to display map
    private MapFragment mMapFragment;
    // GoogleApiClient to get current location
    private GoogleApiClient mGoogleApiClient;
    // Coordinates of current location
    private LatLng mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location_map);
        buildGoogleApiClient();
        requestConnection();
    }

    // Opens the map
    public void initializeMap() {
        mMapFragment = MapFragment.newInstance();
        mMapFragment.getMapAsync(this);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        // Get the FrameLayout that will host the map and make it visible
        fragmentTransaction.add(R.id.map_container, mMapFragment);
        fragmentTransaction.commit();
    }

    // Request connection to GooglePlay Services
    public void requestConnection() {
        mGoogleApiClient.connect();
    }

    // Build the GoogleAPi client
    public void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(mCurrentLocation)
                .title("Marker"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_location_map, menu);
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

    @Override
    public void onConnected(Bundle bundle) {
        // Get the coordinates of the current location
        android.location.Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        Double lat = location.getLatitude();
        Double lng = location.getLongitude();
        mCurrentLocation = new LatLng(lat, lng);
        // After the location has been retrieved, open the map
        initializeMap();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
