package com.p2017capstone2.ipsimplement;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private GPSInfo gps;



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
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(37.56, 126.97)).title("Test"));

        // 맵의 이동
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.56, 126.97), 13));
        init();
    }



    private void init() {
        Log.i("init", "Invoked");

        gps = new GPSInfo(MapsActivity.this);

        // GPS 사용유무 가져오기
        if (gps.isGetLocation()) {
            Log.d("GPS","Get location");

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Log.d("init()", "lat:" + latitude + ", lon:" + longitude);

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            // Showing the current location in Google Map
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.56, 126.97), 15));

            // Map 을 zoom 합니다.
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(16));

            // 마커 설정.
            MarkerOptions optFirst = new MarkerOptions();
            optFirst.position(latLng);// 위도 • 경도
            optFirst.title("Current Position");// 제목 미리보기
            optFirst.snippet("Snippet");
            mGoogleMap.addMarker(optFirst).showInfoWindow();
        }
    }

}