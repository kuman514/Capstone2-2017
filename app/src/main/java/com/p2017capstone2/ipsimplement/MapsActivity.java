package com.p2017capstone2.ipsimplement;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    LocationManager locationManager;
    String locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(37.56, 126.97);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in Seoul"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }



    //------------------------------------------
    //	Override : Location Listener methods
    //------------------------------------------
    @Override
    public void onLocationChanged(Location location) {
        Log.i("called", "onLocationChanged");

        double lat, lon;
        lat = location.getLatitude();
        lon = location.getLongitude();

        //when the location changes, update the map by zooming to the location
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(lat,lon));
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lon)).title("Marker in Seoul"));
        this.mMap.moveCamera(center);

        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        this.mMap.animateCamera(zoom);
    }

    @Override
    public void onProviderDisabled(String arg0) {Log.i("called", "onProviderDisabled");}

    @Override
    public void onProviderEnabled(String arg0) {Log.i("called", "onProviderEnabled");}

    @Override
    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {Log.i("called", "onStatusChanged");}
}
