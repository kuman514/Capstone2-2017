package com.p2017capstone2.ipsimplement;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by kuman514 on 2017-12-03.
 */

public class GPSInfo extends Service implements LocationListener {

    // MapsActivity와 연결하기 위한 인스턴스 변수
    private final Context mContext;

    // 현재 GPS 사용유무
    boolean isGPSEnabled = false;
    // 네트워크 사용유무
    boolean isNetworkEnabled = false;
    // GPS 상태값
    boolean isGetLocation = false;

    // 현재 GPS 위치를 받아오고자 하는 인스턴스 변수
    Location location;  // 위도와 경도를 포함하는 location 인스턴스
    double lat;         // 위도
    double lon;         // 경도

    // GPS 정보 갱신에 필요한 최소 거리
    private static final long MIN_DISTANCE_UPDATES = 0;

    // GPS 정보 갱신 주기
    private static final long MIN_TIME_UPDATES = 0;

    protected LocationManager locationManager;



    public GPSInfo(Context context) {
        this.mContext = context;
        getLocation();
    }



    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
            } else {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // 권한이 부여되지 않은 경우 에러를 로그에 표시
                    Log.e("Permission Denied","Check Permissions to use.");
                }

                this.isGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_UPDATES,
                            MIN_DISTANCE_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            // 위도 경도 저장
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                        }
                    }
                }

                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                        LocationManager.GPS_PROVIDER,
                                        MIN_TIME_UPDATES,
                                        MIN_DISTANCE_UPDATES,
                                        this);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                lat = location.getLatitude();
                                lon = location.getLongitude();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }



    // GPS 종료
    public void stopUsingGPS() {
        if (locationManager != null) {
            locationManager.removeUpdates(GPSInfo.this);
        }
    }



    // 위도값 반환
    public double getLatitude() {
        if (location != null) {
            lat = location.getLatitude();
        }
        return lat;
    }



    // 경도값 반환
    public double getLongitude() {
        if (location != null) {
            lon = location.getLongitude();
        }
        return lon;
    }




    public boolean isGetLocation() {
        return this.isGetLocation;
    }




    // GPS 정보를 가져오지 못했을때 설정값으로 갈지 물어보는 alert 창
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                mContext);

        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다.\n 설정창으로 가시겠습니까?");

        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mContext.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }



    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }



    // 현재 위치가 바뀔 때마다 호출되는 함수
    public void onLocationChanged(Location location) {
        Log.d("GPSLocationChanged", "Invoked");

        // 현재 위치가 바뀔 때마다 location을 업데이트하여 lat과 lon에 저장을 한다.
        // 참고: this.location은 구 데이터, 그냥 location은 업데이트에 쓰일 현 데이터.
        this.location = location;
        if (this.location != null) {
            lat = this.location.getLatitude();
            lon = this.location.getLongitude();
        }

        // MapsActivity에, 갱신된 현재 위치를 받아오도록 요청
        ((MapsActivity) mContext).browseLocation();

        // 샘플 함수 사용. 위치를 테스트하고자 할때 사용함.
        //checkLocation();

        // TODO: 해당 부분에, 위치가 변경될 때마다 실행하고자 하는 로직을 넣으시오.
        // TODO: MapsActivity에서의 표시가 필요한 경우, ((MapsActivity) mContext).MapsActivity멤버함수(); 를 써넣으면 됩니다.

        // ========================================================================================
    }



    void checkLocation() {
        // onLocationChanged() 전용 함수. 샘플 위치 범위(서울)에 있으면 헬로 바스티온을 출력.
        if((127.05 <= lon && lon <= 127.15) && (37.555 <= lat && lat <= 37.565)){
            Log.i("Hello","Bastion");
        }
    }



    // 위치 공급자의 상태가 바뀔 때 호출됨.
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("OnStatusChanged", "provider: " + provider + ", status: " + status + ", extras:" + extras.toString());
    }



    // 위치 공급자가 사용 가능해질(enabled) 때 호출됨.
    // 예 : GPS 기능 ON 등등
    public void onProviderEnabled(String provider) {
        Log.d("OnProviderEnabled", "provider: " + provider);
    }



    // 위치 공급자가 사용 불가능해질(disabled) 때 호출됨.
    // 예 : GPS 기능 OFF 등등
    public void onProviderDisabled(String provider) {
        Log.d("OnProviderDisabled", "provider: " + provider);
    }

}
