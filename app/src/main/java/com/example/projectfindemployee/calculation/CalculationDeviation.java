package com.example.projectfindemployee.calculation;

import java.util.ArrayList;

public class CalculationDeviation {

    public ArrayList<Float> data_x = new ArrayList<>(),
            data_y = new ArrayList<>(),
            data_z = new ArrayList<>();
    public ArrayList<Double> results=new ArrayList<>();
    public ArrayList<Float> dispersion_x = new ArrayList<>(),
            dispersion_y = new ArrayList<>(),
            dispersion_z = new ArrayList<>();
    public Double average_dispersion_x = 0.0,
            average_dispersion_y = 0.0,
            average_dispersion_z = 0.0;
    public Double average_x = 0.0, average_y = 0.0, average_z = 0.0,
            sum_x = 0.0, sum_y = 0.0, sum_z = 0.0;

    public Double sum_x_x = 0.0, sum_y_y = 0.0, sum_z_z = 0.0;
    public boolean calibration, if_compute = false;

    public CalculationDeviation(ArrayList<Float> data_x, ArrayList<Float> data_y, ArrayList<Float> data_z/* View view*/) {
        this.data_x = data_x;
        this.data_y = data_y;
        this.data_z = data_z;
//        Toast.makeText(view.getContext(), "Calibration...", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Double> compute() {

        //average value
        for (int i = 0; i < data_x.size(); i++) {
            sum_x += data_x.get(i);
        }
        for (int i = 0; i < data_y.size(); i++) {
            sum_y += data_y.get(i);
        }
        for (int i = 0; i < data_z.size(); i++) {
            sum_z += data_z.get(i);
        }

        if (data_x.size() != 0 && data_y.size() != 0 && data_z.size() != 0) {
            average_x = sum_x / data_x.size();
            average_y = sum_y / data_y.size();
            average_z = sum_z / data_z.size();
        }


        //average variance
        for (int i = 0; i < data_x.size(); i++) {
            sum_x_x += Math.pow(data_x.get(i) - average_x, 2);
        }
        for (int i = 0; i < data_y.size(); i++) {
            sum_y_y += Math.pow(data_y.get(i) - average_y, 2);
        }
        for (int i = 0; i < data_z.size(); i++) {
            sum_z_z += Math.pow(data_z.get(i) - average_z, 2);
        }
        average_dispersion_x = Math.sqrt(sum_x_x / data_x.size());
        average_dispersion_y = Math.sqrt(sum_y_y / data_y.size());
        average_dispersion_z = Math.sqrt(sum_z_z / data_z.size());

//           average.setText("average\n X: " + average_x + "\n  Y: " + average_y + "\n  Z: " + average_z);
//        dispersion.setText("dispersion\n X: " + average_dispersion_x + "\n  Y: " + average_dispersion_y + "\n  Z: " + average_dispersion_z);

//        results[0]-average_x;
//        results[1]-average_y;
//        results[2]-average_z;
//        results[3]-average_dispersion_x;
//        results[4]-average_dispersion_y;
//        results[5]-average_dispersion_z;
//        results[6]-if_compute;
        results.add(average_x);
        results.add(average_y);
        results.add(average_z);
        results.add(average_dispersion_x);
        results.add(average_dispersion_y);
        results.add(average_dispersion_z);
        results.add(1.0);
        data_x.clear();
        data_y.clear();
        data_z.clear();
        if_compute = true;
        return results;
    }
}
