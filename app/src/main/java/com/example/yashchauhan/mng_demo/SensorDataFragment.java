package com.example.yashchauhan.mng_demo;


import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static android.content.Context.SENSOR_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class SensorDataFragment extends Fragment implements SensorEventListener {


    private TextView value,value2;
    SensorManager sensorManager;
    Sensor mMagnetometer;
    public static DecimalFormat DECIMAL_FORMATTER;
    Context mcontext;

    @SuppressLint("ValidFragment")
    public SensorDataFragment(Context mcontext) {
        this.mcontext = mcontext;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_basic_globe, container, false);

        // Inflate the layout for this fragment
        value = (TextView) rootView.findViewById(R.id.value);
        value2 = (TextView) rootView.findViewById(R.id.disp_location);
        // define decimal formatter
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setDecimalSeparator('.');
        DECIMAL_FORMATTER = new DecimalFormat("#.000", symbols);
        sensorManager = (SensorManager) mcontext.getSystemService(SENSOR_SERVICE);
        mMagnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);

//        return inflater.inflate(R.layout.fragment_sensor_data, container, false);
        return rootView;
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

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
