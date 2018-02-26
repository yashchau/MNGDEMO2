package com.example.yashchauhan.mng_demo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.LookAt;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globe.BasicElevationCoverage;
import gov.nasa.worldwind.layer.BackgroundLayer;
import gov.nasa.worldwind.layer.BlueMarbleLandsatLayer;
import gov.nasa.worldwind.layer.RenderableLayer;
import gov.nasa.worldwind.render.Color;
import gov.nasa.worldwind.render.ImageSource;
import gov.nasa.worldwind.shape.Path;
import gov.nasa.worldwind.shape.Placemark;
import gov.nasa.worldwind.shape.PlacemarkAttributes;
import gov.nasa.worldwind.shape.ShapeAttributes;
//import gov.nasa.worldwind.shape.ShapeAttributes;

import static android.content.ContentValues.TAG;
import static android.content.Context.SENSOR_SERVICE;


///**
// * A simple {@link Fragment} subclass.
// * Use the {@link BasicGlobeFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
@SuppressLint("ValidFragment")
public class BasicGlobeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener, SensorEventListener {

     Context mcontext;
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    SensorManager sensorManager;
    Sensor mMagnetometer;
    public WorldWindow wwd;
    public FrameLayout globeLayout;
    LookAt lookAt = new LookAt().set(12.9723793, 77.685245, 0, WorldWind.ABSOLUTE, 2e4 /*range*/, 0 /*heading*/, 45 /*tilt*/, 0 /*roll*/);
    double lat[]={12.9723793,12.5724,12.973,12.9722,12.972},lon[]={77.685245,77.685,77.686,77.6856,77.6845},alt=0, tempx[]={0.005,0,0,0,0},tempy[]={0.005,0,0,0,0};
    Position pos = new Position(lat[0], lon[0], 0);
    Position pos1 = new Position(lat[1], lon[1], 0);
    Position pos2 = new Position(lat[2], lon[2], 0);
    Position pos3 = new Position(lat[3], lon[3], 0);
    Position pos4 = new Position(lat[4], lon[4], 0);
    RenderableLayer layer = new RenderableLayer("Placemarks");
    public Placemark tank2, tank3,tank4,tank5;

    List<Position> positions = Arrays.asList(
            Position.fromDegrees(lat[0], lon[0],1000),
            Position.fromDegrees(lat[1], lon[1],1000)
//            Position.fromDegrees(lat[2], lon[2], 1000),
//            Position.fromDegrees(lat[3], lon[3],1000),
//            Position.fromDegrees(lat[4], lon[4], 1000)
//            Position.fromDegrees(0,0,0)
    );
    ShapeAttributes attrs = new ShapeAttributes();

//        attrs.setDrawVerticals(true); // display the extruded verticals
//        attrs.setInteriorColor(new Color(1, 1, 1, 0.5f)); // 50% transparent white
//        attrs.setOutlineWidth(3);
//    Path path = new Path(positions,attrs);
//    private GPS gps;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation, mCurrentLocation;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    String mLastUpdateTime;
    //    private GPS gps;
    TextView location;

    public BasicGlobeFragment(Context mcontext) {
        this.mcontext = mcontext;
        // Required empty public constructor
    }



    /**
     * Creates a new WorldWindow (GLSurfaceView) object.
     */
    public WorldWindow createWorldWindow() {
        // Create the WorldWindow (a GLSurfaceView) which displays the globe.
        wwd = new WorldWindow(getContext());

        // Setup the WorldWindow's layers.
        wwd.getLayers().addLayer(new BackgroundLayer());
        wwd.getLayers().addLayer(new BlueMarbleLandsatLayer());
        // Setup the WorldWindow's elevation coverages.
        wwd.getGlobe().getElevationModel().addCoverage(new BasicElevationCoverage());
        displayLocation();
        tank2 = createPlacemark(pos1,layer);
        tank3 = createPlacemark(pos2,layer);
//        tank4 = createPlacemark(pos3,layer);
//        tank5 = createPlacemark(pos4,layer);
        attrs.setDrawVerticals(true); // display the extruded verticals
        attrs.setInteriorColor(new Color(1, 1, 1, 1f)); // 50% transparent white
        attrs.setOutlineWidth(10);
        Path path = new Path(positions,attrs);
        path.setExtrude(true); // extrude the path from the ground to each path position's altitude
        layer.addRenderable(path);

//        positions = Arrays.asList(
//                Position.fromDegrees(20, -180, 1e5),
//                Position.fromDegrees(0, -100, 1e6),
//                Position.fromDegrees(20, -40, 1e5)
//        );
//        ShapeAttributes attrs = new ShapeAttributes();
//        attrs.setDrawVerticals(true); // display the extruded verticals
//        attrs.setInteriorColor(new Color(1, 1, 1, 0.5f)); // 50% transparent white
//        attrs.setOutlineWidth(3);
//        path = new Path(positions, attrs);
//        path.setExtrude(true); // extrude the path from the ground to each path position's altitude
//        layer.addRenderable(path);
        return wwd;
    }

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment BasicGlobeFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static BasicGlobeFragment newInstance(String param1, String param2) {
//        BasicGlobeFragment fragment = new BasicGlobeFragment();
//        Bundle args = new Bundle();
////        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.gps = new GPS(this);
        buildGoogleApiClient();
//        displayLocation();
//        layer.addRenderable(path);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_basic_globe, container, false);

        globeLayout = (FrameLayout) rootView.findViewById(R.id.globe);
        location = (TextView) rootView.findViewById(R.id.location_basicglobe);
        globeLayout.addView(createWorldWindow());
        wwd.getNavigator().setAsLookAt(wwd.getGlobe(), lookAt);
        sensorManager = (SensorManager) mcontext.getSystemService(SENSOR_SERVICE);
        mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
//        layer.addRenderable(path);
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_basic_globe, container, false);
        return rootView;
    }

    @SuppressLint("SetTextI18n")
    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLastLocation != null) {
            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            location.setText(latitude + ", " + longitude);
            wwd.getNavigator().setAsLookAt(wwd.getGlobe(), lookAt.set(latitude,longitude, 0, WorldWind.ABSOLUTE, 2e4 /*range*/, 0 /*heading*/, 45 /*tilt*/, 0 /*roll*/));
            tank2.setPosition(pos.set(latitude,longitude,0));
        } else {

            location.setText("(Couldn't get the location. Make sure location is enabled on the device)");
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity().getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.wwd.onResume();
        sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);

//        checkPlayServices();
    }

//    private boolean checkPlayServices() {
//
//        int resultCode = GooglePlayServicesUtil
//                .isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
//                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
//                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
////                Toast.makeText("This device is not supported.", Toast.LENGTH_LONG)
////                        .show();
////                finish();
//            }
//            return false;
//        }
//        return true;
//    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("TAG", "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
        displayLocation();
//        updateUI();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("TAG", "Firing onLocationChanged..............................................");
        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mCurrentLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        // mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
//        updateUI();
        displayLocation();
    }


    @Override
    public void onPause() {
        super.onPause();
        this.wwd.onPause(); // pauses the rendering thread
    }



    protected Placemark createPlacemark(Position position, RenderableLayer layer) {
        Placemark placemark = new Placemark(position);
        placemark.getAttributes().setImageSource(ImageSource.fromResource(R.drawable.aircraft_fixwing));
        placemark.getAttributes().setImageScale(2);
        placemark.getAttributes().setDrawLeader(true);
        placemark.setHighlightAttributes(new PlacemarkAttributes(placemark.getAttributes()).setImageScale(4));
        layer.addRenderable(placemark);
        wwd.getLayers().addLayer(layer);
        return placemark;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // get values for each axes X,Y,Z
//            String smagX = String.valueOf(magX);
            Log.d("magnetometer values","Entered onSensorChanged" );

//        if (event.sensor == mMagnetometer){
            float magX = event.values[0];
            float magY = event.values[1];
            float magZ = event.values[2];
            String smagx = String.valueOf(magX);
            String smagy = String.valueOf(magY);
            String smagz = String.valueOf(magZ);
            double magnitude = Math.sqrt((magX * magX) + (magY * magY) + (magZ * magZ));
            // set value on the screen
//            value.setText(smagx + " , " +smagy + " , " + " , " + smagz);
//            value2.setText((int) (event.values[0] + event.values[1] + event.values[2]));
//            value.setText(DECIMAL_FORMATTER.format(magnitude) + " \u00B5Tesla");
//            value2.setText(DECIMAL_FORMATTER.format(magnitude) + " \u00B5Tesla");
            Log.d("magnetometer values",smagx + " , " +smagy + " , " + " , " + smagz);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


//
//    public interface IGPSActivity {
//        public void locationChanged(double longitude, double latitude);
//        public void displayGPSSettingsDialog();
//    }
//
//    public void locationChanged(double longitude, double latitude) {
//        Log.d(TAG, "Main-Longitude: " + longitude);
//        Log.d(TAG, "Main-Latitude: " + latitude);
//    }
//
//
//    @Override
//    public void displayGPSSettingsDialog() {
//        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//        startActivity(intent);
//    }
}
