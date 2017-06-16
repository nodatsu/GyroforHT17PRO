package com.example.tsunoda.gyroforht17pro;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private TextView textView, textInfo;
    private float sensorX;
    private float sensorY;
    private float sensorZ;
    private boolean flg = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        textInfo = (TextView) findViewById(R.id.text_info);

        // Get an instance of the TextView
        textView = (TextView) findViewById(R.id.text_view);

        Log.d("debug","onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Listenerの登録
        Sensor gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(gyro != null){
            sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("debug","registerListener");
        }
        else{
            textView.setText("No Support");
        }

//        int period = 1000;
//        sensorManager.registerListener(this, gyro,period);

        // boolean registerListener (SensorEventListener listener, Sensor sensor, int samplingPeriodUs)
//        sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_FASTEST);
//        sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_UI);
//        sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_GAME);
        Log.d("debug","onResume");
    }

    // 解除するコードも入れる!
    @Override
    protected void onPause() {
        super.onPause();
        // Listenerを解除
        sensorManager.unregisterListener(this);

        Log.d("debug","onPause");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("debug","onSensorChanged");

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            sensorX = event.values[0];
            sensorY = event.values[1];
            sensorZ = event.values[2];

            String strTmp = "Gyroscope\n"
                    + " X: " + sensorX + "\n"
                    + " Y: " + sensorY + "\n"
                    + " Z: " + sensorZ;
            textView.setText(strTmp);

            if(flg){
                showInfo(event);
            }
        }

    }

    // センサーの各種情報を表示する
    private void showInfo(SensorEvent event){
        String info = "Name: " + event.sensor.getName() + "\n";
        info += "Vendor: " + event.sensor.getVendor() + "\n";
        info += "Type: " + event.sensor.getType() + "\n";
        info += "StringType: " + event.sensor.getStringType()+ "\n";

        int data = event.sensor.getMinDelay();
        info += "Mindelay: "+String.valueOf(data) +" usec\n";

        data = event.sensor.getMaxDelay();
        info += "Maxdelay: "+String.valueOf(data) +" usec\n";

        data = event.sensor.getReportingMode();
        String stinfo = "unknown";
        if(data == 0){
            stinfo = "REPORTING_MODE_CONTINUOUS";
        }else if(data == 1){
            stinfo = "REPORTING_MODE_ON_CHANGE";
        }else if(data == 2){
            stinfo = "REPORTING_MODE_ONE_SHOT";
        }
        info += "ReportingMode: "+stinfo +" \n";

        float fData = event.sensor.getMaximumRange();
        info += "MaxRange: "+String.valueOf(fData) +" \n";

        fData = event.sensor.getResolution();
        info += "Resolution: "+String.valueOf(fData) +" m/s^2 \n";

        fData = event.sensor.getPower();
        info += "Power: "+String.valueOf(fData) +" mA\n";

        textInfo.setText(info);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}