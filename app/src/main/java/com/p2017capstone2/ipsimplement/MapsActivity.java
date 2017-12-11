package com.p2017capstone2.ipsimplement;

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

        // googleMap 인스턴스를 생성하기 위한 로직
        MapsInitializer.initialize(getApplicationContext());
        GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
        // 이후 onMapReady()를 참조
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.i("OnMapReady", "Invoked");

        // 여기서 mGoogleMap이 구글맵 인스턴스를 받아와서 지도에 마커 표시 등등을 한다.
        mGoogleMap = googleMap;
        // GPSInfo 인스턴스를 만든 뒤, 현재 위치를 바로 가져온다.
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

            // 현재 위치의 위도값과 경도값을 받아온다.
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            Log.d("browseLocation()", "lat:" + latitude + ", lon:" + longitude);

            // 현재 위치에 대한 LanLng 인스턴스를 생성
            LatLng latLng = new LatLng(latitude, longitude);

            // 마커 설정.
            if(optFirst != null) optFirst = null;
            optFirst = new MarkerOptions();
            optFirst.position(latLng);
            optFirst.title("Current Position");
            optFirst.snippet("Snippet");

            // 마커에 표시
            mGoogleMap.addMarker(optFirst).showInfoWindow();

            // 지도를 현재 위치를 보도록 카메라 돌리기
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            // 지도 줌 인
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

}