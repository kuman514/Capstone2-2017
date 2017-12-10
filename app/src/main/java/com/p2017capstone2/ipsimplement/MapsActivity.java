package com.p2017capstone2.ipsimplement;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private GPSInfo gps;
    MarkerOptions optFirst;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("OnCreate", "Invoked");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // BitmapDescriptorFactory 생성하기 위한 소스
        MapsInitializer.initialize(getApplicationContext());
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("OnMapReady", "Invoked");

        mGoogleMap = googleMap;
        //mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(37.56, 126.97)).title("Test"));
        //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.56, 126.97), 13));

        init();
    }



    private void init() {
        Log.i("init", "Invoked");
        gps = new GPSInfo(MapsActivity.this);
        browseLocation();
    }



    public void browseLocation(){
        // GPS 사용유무 가져오기
        Log.d("browseLocation()", "Invoked");
        if (gps.isGetLocation()) {
            Log.d("browseLocation()","GPS Gets location");

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Log.d("browseLocation()", "lat:" + latitude + ", lon:" + longitude);

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            // 마커 설정.
            if(optFirst != null) optFirst = null;
            optFirst = new MarkerOptions();
            optFirst.position(latLng);
            optFirst.title("Current Position");
            optFirst.snippet("Snippet");
            mGoogleMap.addMarker(optFirst).showInfoWindow();

            // Showing the current location in Google Map
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            // Map 을 zoom 합니다.
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        }
    }

}