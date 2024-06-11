package com.example.projectfindemployee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projectfindemployee.MainActivity;
import com.example.projectfindemployee.R;
import com.example.projectfindemployee.map.MapOfEmployeeView;

import java.util.ArrayList;

public class MapOfEmployee extends Fragment {
    ArrayList<Double> coordinates_data;
    TextView coordinates_text;
    Button btn_calibrate, btn_clear;
    public double x=0.0,y=0.0,z=0.0;
    MainActivity mainActivity;
    public MapOfEmployeeView employeeCard;

    Double coordinate_x = 0.0, coordinate_y = 0.0, coordinate_z = 0.0;
    View v;
    Bundle bundle;

    public MapOfEmployee(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = savedInstanceState;


    }

    public void onStartThread(boolean thread_flag) {
        Toast.makeText(getContext(), thread_flag + "", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {


                while (thread_flag) {
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                     x = mainActivity.coordinates.x_now;
                     y = mainActivity.coordinates.y_now;
                     z = mainActivity.coordinates.z_now;
                    employeeCard.setCoordinates(x, y, z);

                }
            }
        }).start();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        coordinates_text = getView().findViewById(R.id.coordinates);
        btn_calibrate = getView().findViewById(R.id.btn_calibrate);
        btn_clear = getView().findViewById(R.id.btn_clear_coordinates);
        employeeCard = (MapOfEmployeeView) getView().findViewById(R.id.map_of_employee);
//        employeeCard.setContext(getContext());
//        coordinates_data = new ArrayList<>();
        btn_calibrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Calibration...", Toast.LENGTH_SHORT).show();
                mainActivity.calibration = true;
                long time1 = System.currentTimeMillis();
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.coordinates.x_now = 0.5;
                mainActivity.coordinates.y_now = 0.5;
                mainActivity.coordinates.z_now = 0.5;
                mainActivity.coordinates.x_last = 0.5;
                mainActivity.coordinates.y_last = 0.5;
                mainActivity.coordinates.z_last = 0.5;
            }
        });

    }

    public void setTextCoordinates() {

//            Double x=coordinates_data.get(0)*1000,y=coordinates_data.get(1)*1000,z=coordinates_data.get(2)*1000;
//            Integer x_1=x.intValue(),y_1=y.intValue(),z_1=z.intValue();
//            Double x_2=x_1/1000.1,y_2=y_1/1000.1,z_2=z_1/1000.1;
         x = mainActivity.coordinates.x_now;
         y = mainActivity.coordinates.y_now;
         z = mainActivity.coordinates.z_now;
        coordinates_text.setText("X: " + x + "\nY: " + y + "\nZ: " + z);


    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
