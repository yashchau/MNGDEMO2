package com.example.yashchauhan.mng_demo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.LocationListener;

/**
 * Created by yashchauhan on 23/02/18.
 */

//public class GPS extends Context {
//
//    private BasicGlobeFragment.IGPSActivity main;
//
//    // Helper for GPS-Position
//    private LocationListener mlocListener;
//    private LocationManager mlocManager;
//
//    private boolean isRunning;
//
//    public GPS(BasicGlobeFragment main) {
//        this.main = (BasicGlobeFragment.IGPSActivity) main;
//
//        // GPS Position
//        mlocManager = (LocationManager) ((Activity) this.main).getSystemService(Context.LOCATION_SERVICE);
//        mlocListener = new MyLocationListener();
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) mlocListener);
//        // GPS Position END
//        this.isRunning = true;
//    }
//
//    public void stopGPS() {
//        if (isRunning) {
//            mlocManager.removeUpdates((android.location.LocationListener) mlocListener);
//            this.isRunning = false;
//        }
//    }
//
//    public void resumeGPS() {
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) mlocListener);
//        this.isRunning = true;
//    }
//
//    public boolean isRunning() {
//        return this.isRunning;
//    }
//
//    public class MyLocationListener implements LocationListener {
//
//        private final String TAG = MyLocationListener.class.getSimpleName();
//
//        @Override
//        public void onLocationChanged(Location loc) {
//            GPS.this.main.locationChanged(loc.getLongitude(), loc.getLatitude());
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//            GPS.this.main.displayGPSSettingsDialog();
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//
//        }
//
//    }
//
//    public interface IGPSActivity {
//        public void locationChanged(double longitude, double latitude);
//        public void displayGPSSettingsDialog();
//    }
//}