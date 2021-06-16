package com.example.appmuseu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor lightSensor;
    private TextView sensorluz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        sensorluz = findViewById(R.id.txtsensor);
        sensorManager = (SensorManager) getSystemService(Service.SENSOR_SERVICE);
        lightSensor = (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));

    }

    @Override
    protected void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float light = event.values[0];
        sensorluz.setText(String.valueOf("A luminosidade do ambiente é " + light));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}