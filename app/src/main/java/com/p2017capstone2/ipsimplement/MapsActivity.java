package com.p2017capstone2.ipsimplement;

import android.content.Intent;
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

    // 임시 구현 : 숭실대입구역 각 출구 위치
    LatLng[] SSUStation = {new LatLng(37.49506, 126.95444),
                            new LatLng(37.49563, 126.95395),
                            new LatLng(37.49588, 126.95420),
                            new LatLng(37.49529, 126.95473)};
    // 임시 구현 : 임시 오차값
    double tmpErr = 0.000003;



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

        // 마커 설정
        optFirst = new MarkerOptions();
        optFirst.title("Current Position");
        optFirst.snippet("It's you.");

        // 현재 위치 가져오기
        browseLocation();

        // 지도 줌 인
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
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

            // 모든 마커 지우기
            mGoogleMap.clear();

            // 마커의 위치 설정
            optFirst.position(latLng);

            // 마커에 표시
            mGoogleMap.addMarker(optFirst).showInfoWindow();

            // 지도를 현재 위치를 보도록 카메라 돌리기
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

            // 특정 위치 도달 시 액티비티 전환하기
            goToAnotherActivity(latLng);
        }
    }



    // 임시 구현된 액티비티로 전환시키기
    void goToAnotherActivity(LatLng ll) {
        // 임시 구현 : 화면 전환을 위한 액티비티 임시 구현 할당
        // 임시 구현 : 특정 지역에 도착 시 액티비티를 전환시킴
        for(int i = 0; i < 4; i++)
            if((SSUStation[i].longitude - tmpErr < ll.longitude && ll.longitude < SSUStation[i].longitude + tmpErr) &&
                    (SSUStation[i].latitude - tmpErr < ll.latitude && ll.latitude < SSUStation[i].latitude + tmpErr))
                // TODO: 여기에 변환시키고자 할 액티비티를 다음 포맷으로 기입하시오.
                // startActivity(new Intent(this, 액티비티이름.class));
                // CAUTION: 액티비티를 전환하고자 한다면, 먼저 반드시 매니페스트에 액티비티를 등록하세요.
                startActivity(new Intent(this, TempActivity.class));
    }

}