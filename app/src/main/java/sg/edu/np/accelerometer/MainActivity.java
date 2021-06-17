package sg.edu.np.accelerometer;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView stepCounterDisplay;
    private double magnitudePrevious = 0;
    private int noOfSteps = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stepCounterDisplay = findViewById(R.id.stepCounter);
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener stepCount = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event != null){
                    float x_axis_value = event.values[0];
                    float y_axis_value = event.values[1];

                    float z_axis_value = event.values[2];
                    double magnitude = Math.sqrt(x_axis_value*x_axis_value + y_axis_value*y_axis_value + z_axis_value*z_axis_value);
                    double diff = magnitude - magnitudePrevious;
                    magnitudePrevious = magnitude;
                    if(diff > 6){
                        noOfSteps++;
                    }
                    stepCounterDisplay.setText(String.valueOf(noOfSteps));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepCount, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
}