package com.example.projectfindemployee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectfindemployee.MainActivity;
import com.example.projectfindemployee.R;
import com.example.projectfindemployee.model.Employee;
import com.example.projectfindemployee.retrofit.EmployeeApi;
import com.example.projectfindemployee.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Registration extends Fragment {
    EditText name_field,password_field,mail_field,surname_field;
    Button btn_sign_up;
    MainActivity mainActivity;

    public Registration(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false);
    }
    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name_field=getView().findViewById(R.id.entry_field_name);
        password_field=getView().findViewById(R.id.entry_field_surname);
        mail_field=getView().findViewById(R.id.entry_field_mail);
        surname_field=getView().findViewById(R.id.entry_field_password);
        btn_sign_up=getView().findViewById(R.id.button_sign_up);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitService retrofitService =new RetrofitService();
                EmployeeApi employeeApi =  retrofitService.getRetrofit().create(EmployeeApi.class);
                String name=String.valueOf(name_field.getText());
                String surname=String.valueOf(surname_field.getText());
                String mail=String.valueOf(mail_field.getText());
                String password=String.valueOf(password_field.getText());
                Employee employee=new Employee();
                employee.setName(name);
                employee.setSurname(surname);
                employee.setMail(mail);
                employee.setPassword(password);
                employeeApi.save(employee).enqueue(new Callback<Employee>() {
                    @Override
                    public void onResponse(Call<Employee> call, Response<Employee> response) {
                        Toast.makeText(mainActivity, "Sign up successful!", Toast.LENGTH_SHORT).show();
                        mainActivity.if_registered=true;
                        mainActivity.changeFragment(mainActivity.profile);
                    }

                    @Override
                    public void onFailure(Call<Employee> call, Throwable t) {
                        Toast.makeText(mainActivity, "Sign up failed!", Toast.LENGTH_SHORT).show();
                        Toast.makeText(mainActivity, employee.toString(), Toast.LENGTH_SHORT).show();
//                        Logger.getLogger(mainActivity.class.getName()).log(Level.SEVERE,"Error occurred",t);
                    }
                });
            }
        });
    }

}