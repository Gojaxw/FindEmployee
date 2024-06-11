package com.example.projectfindemployee.calculation;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class AccelerationData implements SensorEventListener {
    int SensorType = Sensor.TYPE_ACCELEROMETER;//Sensor.TYPE_LINEAR_ACCELERATION;
    ArrayList<Float> data_x = new ArrayList<>(),
            data_y = new ArrayList<>(),
            data_z = new ArrayList<>();
    ArrayList<Double> data = new ArrayList<>(),
                    coordinates_data =new ArrayList<>();
    public Double average_dispersion_x = 0.0, average_dispersion_y = 0.0, average_dispersion_z = 0.0;
    public Double average_x = 0.0, average_y = 0.0, average_z = 0.0, sum_x = 0.0, sum_y = 0.0, sum_z = 0.0;
    public boolean calibration=false, if_compute = false;
    public long time_last = 0, time_now = 0, del_time = 0;
    public Coordinates coordinates;
    public View view;

    public AccelerationData(View v) {
        this.view = v;
        Toast.makeText(v.getContext(),"acceleration",Toast.LENGTH_SHORT).show();
    }

    public void startCalibration(){
        calibration=true;
        Toast.makeText(view.getContext(),"1",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (calibration) {
            data_x.add(event.values[0]);
            data_y.add(event.values[1]);
            data_z.add(event.values[2]);
            Toast.makeText(view.getContext(),"2",Toast.LENGTH_SHORT).show();
        }
        if (data_x.size() > 200) {
            Toast.makeText(view.getContext(),"calculation",Toast.LENGTH_SHORT).show();
            calibration = false;
            CalculationDeviation calculation =new CalculationDeviation(data_x,data_y,data_z);
            data=calculation.compute();

            if(if_compute){
                average_x=calculation.average_x;
                average_y=calculation.average_y;
                average_z=calculation.average_z;
                average_dispersion_x= calculation.average_dispersion_x;
                average_dispersion_y=calculation.average_dispersion_y;
                average_dispersion_z= calculation.average_z;
            }
        }
        if (event.sensor.getType() == SensorType) {
            if (if_compute) {
                time_now = System.currentTimeMillis();
                if (time_last == 0) {
                    del_time = System.currentTimeMillis() - time_now;
                } else {
                    del_time = time_now - time_last;
                }
                //Toast.makeText(MainActivity.this,""+average_dispersion_z,Toast.LENGTH_SHORT).show();
//              coordinates_data = coordinates.calculateCoordinate(new MapOfEmployee() average_dispersion_x, average_dispersion_y, average_dispersion_z,
//                        del_time, event.values[0], event.values[1], event.values[2], average_x, average_y, average_z);
//                time_last = time_now;
            }


        }
    }
    public ArrayList<Double> getCoordinates(){
        return  coordinates_data;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
