package com.example.projectfindemployee;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectfindemployee.calculation.CalculationDeviation;
import com.example.projectfindemployee.calculation.Coordinates;
import com.example.projectfindemployee.fragments.Authentication;
import com.example.projectfindemployee.fragments.MapOfEmployee;
import com.example.projectfindemployee.fragments.Profile;
import com.example.projectfindemployee.fragments.Registration;
import com.example.projectfindemployee.fragments.Search;
import com.example.projectfindemployee.fragments.Settings;
import com.example.projectfindemployee.model.Employee;
import com.example.projectfindemployee.retrofit.EmployeeApi;
import com.example.projectfindemployee.retrofit.RetrofitService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //***User Interface***

    //Fragments
   public Profile profile = new Profile(this);
   public Search search = new Search();
   public Authentication authentication = new Authentication(this);
   public Registration registration = new Registration(this);
    //Change

   public Settings settings = new Settings();
   public MapOfEmployee mapOfEmployee = new MapOfEmployee(this);

    public Employee employee;
    //View
  public   BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    //Sensor data
    int SensorType = Sensor.TYPE_ACCELEROMETER;//Sensor.TYPE_LINEAR_ACCELERATION;
  public   Double x12=0.0,y12=0.0;
    ArrayList<Float> data_x = new ArrayList<>(),
            data_y = new ArrayList<>(),
            data_z = new ArrayList<>();
    public ArrayList<Double> data = new ArrayList<>(),
            coordinates_data = new ArrayList<>(),
            results = new ArrayList<>();
    public Double average_dispersion_x = 0.0, average_dispersion_y = 0.0, average_dispersion_z = 0.0;
    public Double average_x = 0.0, average_y = 0.0, average_z = 0.0, sum_x = 0.0, sum_y = 0.0, sum_z = 0.0;
    public boolean calibration = false, if_compute = false,

                    if_registered=false;
    public long time_last = 0, time_now = 0, del_time = 0;
    Employee eqads;
    public Coordinates coordinates;
    public View view;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        //Initialization
employee =new Employee();
eqads =new Employee();
        bottomNavigationView = findViewById(R.id.bottom_navigation_menu);

        coordinates = new Coordinates();
        view = mapOfEmployee.getView();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    if (item.getItemId() == R.id.profile) {
                        changeFragment(profile);
                        return true;
                    } else if (item.getItemId() == R.id.search) {
                        changeFragment(search);
                        return true;
                    } else if (item.getItemId() == R.id.settings) {
                        changeFragment(settings);
                        return true;
                    } else if (item.getItemId() == R.id.map_of_employee) {
                        changeFragment(mapOfEmployee);
                        return true;
                    }



                    Toast.makeText(getBaseContext(),"Please sign in",Toast.LENGTH_SHORT).show();

                return false;
            }
        });
        if (sManager != null) {
            Sensor alSensor = sManager.getDefaultSensor(SensorType);
            if (alSensor != null) {
                sManager.registerListener((SensorEventListener) this, alSensor, SensorManager.SENSOR_DELAY_GAME);

            }
        } else {
            Toast.makeText(this, "Sensor service not detected.", Toast.LENGTH_SHORT).show();
        }

       changeFragment(authentication);

    }

    public void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //***Sensor data calculation***

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (data_x.size() > 200) {
            Toast.makeText(MainActivity.this, "Сalibration ended!", Toast.LENGTH_SHORT).show();
            calibration = false;
            CalculationDeviation calculationDeviation = new CalculationDeviation(data_x, data_y, data_z);
            results = calculationDeviation.compute();
            average_x = results.get(0);
            average_y = results.get(1);
            average_z = results.get(2);
            average_dispersion_x = results.get(3);
            average_dispersion_y = results.get(4);
            average_dispersion_z = results.get(5);
            if_compute = true;
//            mapOfEmployee.onStartThread(if_compute);
        }
        if (event.sensor.getType() == SensorType) {

            if (if_compute) {
                RetrofitService retrofitService =new RetrofitService();
                EmployeeApi employeeApi =  retrofitService.getRetrofit().create(EmployeeApi.class);
                time_now = System.currentTimeMillis();
                if (time_last == 0) {
                    del_time = System.currentTimeMillis() - time_now;
                } else {
                    del_time = time_now - time_last;
                }
                //Toast.makeText(MainActivity.this,""+average_dispersion_z,Toast.LENGTH_SHORT).show();
                coordinates.calculateCoordinate(/*coordinates,*/ mapOfEmployee, average_dispersion_x, average_dispersion_y, average_dispersion_z,
                        del_time, event.values[0], event.values[1], event.values[2], if_compute, average_x, average_y, average_z);
                mapOfEmployee.employeeCard.setCoordinates(coordinates.x_now, coordinates.y_now, coordinates.z_now);
                time_last = time_now;

                employeeApi.getEmployeeCoordinate().enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {

                          eqads     = (Employee) response.body();

                    }

                    @Override
                    public void onFailure(Call<List<Employee>> call, Throwable t) {

                    }
                });

                mapOfEmployee.employeeCard.setCoordinates1();

                employee.setCoordinateX(coordinates.x_now);
                employee.setCoordinateY(coordinates.y_now);


                employeeApi.change(employee).enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {

                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                    }
                });

            }
            if (calibration) {
                data_x.add(event.values[0]);
                data_y.add(event.values[1]);
                data_z.add(event.values[2]);

            }
//            ((TextView) findViewById(R.id.textView2)).setText("X: " + event.values[0] + "   , Y:" + event.values[1] + "  , Z:" + event.values[2]);
           /* Double xo=event.values[0]-average_x,yo=event.values[1]-average_y,zo=event.values[2]-average_z;
            ((TextView) findViewById(R.id.change_coordinates)).setText("X: " + xo + "   , Y:" +yo + "  , Z:" + zo);*/
        }
    }

    //    public ArrayList<Double> getCoordinates(){
//        return  coordinates_data;
//    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}