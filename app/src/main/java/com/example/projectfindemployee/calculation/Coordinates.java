package com.example.projectfindemployee.calculation;

import com.example.projectfindemployee.fragments.MapOfEmployee;

import java.util.ArrayList;

public class Coordinates {
    public Double x_now = 0.0, y_now = 0., z_now = 0.,
            x_last = 0., y_last = 0., z_last = 0.;
    public ArrayList<Double> results =new ArrayList<>();
    int tolerate = 0;

    public Coordinates(Double x, Double y){
        this.x_now=x;
        this.y_now=y;
    }
    public Coordinates(Double x, Double y, Double z) {
        this.x_now = x;
        this.y_now = y;
        this.z_now = z;
    }

    public Coordinates() {
        this.x_now = 0.0;
        this.y_now = 0.0;
        this.z_now = 0.0;

    }

  public void calculateCoordinate(MapOfEmployee map, Double average_dispersion_x, Double average_dispersion_y, Double average_dispersion_z,
                                               Long time, Float acceleration_x, Float acceleration_y, Float acceleration_z,boolean compute,
                                               Double average_x, Double average_y, Double average_z) {
      if (compute) {
          //Toast.makeText(map.getContext(), " "+time, Toast.LENGTH_SHORT).show();
          double acc_x = acceleration_x - average_x;
          double acc_y = -acceleration_y + average_y;
          double acc_z = acceleration_z - average_z;
          double two = 2.0;
          double one = 1.0;
          double base_x = 0;
          double base_y = 0;
          double base_z = 0;


          if (Math.abs(acc_x) >= average_dispersion_x * 3) {
              tolerate = 0;
              base_x += acc_x;
              Double temp_x = x_now;
              x_now = two * x_now - one * x_last + base_x * time * time / 1000000;

              x_last = temp_x;
          } else if (tolerate < 20) {
              tolerate++;
          } else {

              x_now = x_last;
          }

          if (Math.abs(acc_y) >= average_dispersion_y * 3) {
              tolerate = 0;
              base_y += acc_y;
              Double temp_y = y_now;
              y_now = two * y_now - one * y_last + base_y * time * time / 1000000;

              y_last = temp_y;
          } else if (tolerate < 20) {
              tolerate++;
          } else {

              y_now = y_last;
          }

          if (Math.abs(acc_z) >= average_dispersion_z * 3) {
              tolerate = 0;
              base_z += acc_z;
              Double temp_z = z_now;
              z_now = two * z_now - one * z_last + base_z * time * time / 1000000;

              z_last = temp_z;
          } else if (tolerate < 20) {
              tolerate++;
          } else {
              z_now = z_last;
          }


          // Toast.makeText(map.getContext(), " "+results.get(0)+" "+y_now, Toast.LENGTH_SHORT).show();
          map.setTextCoordinates();

          //((TextView)v).setText("X: " + x_now + "\n Y: " + y_now + "\n Z: " + z_now);
          //((TextView) view).setText("X: " + (double)Math.round(x_now*1000)/1000 + "\n Y: " +(double)Math.round(y_now*1000)/1000 + "\n Z: " + (double)Math.round(z_now*1000)/1000);
      }

    }

    public void clearCoordinates(){
        x_now = 0.0;
        y_now = 0.;
        z_now = 0.;

        x_last = 0.;
        y_last = 0.;
        z_last = 0.;
    }

}