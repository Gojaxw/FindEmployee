package com.example.projectfindemployee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectfindemployee.MainActivity;
import com.example.projectfindemployee.R;
import com.example.projectfindemployee.model.Employee;
import com.example.projectfindemployee.retrofit.EmployeeApi;
import com.example.projectfindemployee.retrofit.RetrofitService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Authentication extends Fragment {

    Button btn_sign_up,btn_sign_in;
    TextInputEditText password_field,mail_field,id_field;
    MainActivity mainActivity;

    public Authentication(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_authentication, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_sign_up= getView().findViewById(R.id.btn_to_sign_up);
        btn_sign_in=getView().findViewById(R.id.btn_to_sign_in);
        password_field=getView().findViewById(R.id.entry_field_password);
        mail_field=getView().findViewById(R.id.entry_field_mail);
        id_field=getView().findViewById(R.id.entry_field_id);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.changeFragment(mainActivity.registration);
            }
        });
        btn_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=String.valueOf(mail_field.getText());
                String password=String.valueOf(password_field.getText());
                Employee employee =new Employee();
                Integer id= Integer.valueOf(""+ String.valueOf( id_field.getText()+""));
                RetrofitService retrofitService = new RetrofitService();
                employee.setId(id);
                employee.setMail(mail);
                employee.setPassword(password);
                employee.setName("");
                employee.setSurname("");

                EmployeeApi employeeApi = retrofitService.getRetrofit().create(EmployeeApi.class);
                employeeApi.getEmployee(employee).enqueue(new Callback<List<Employee>>() {
                    @Override
                    public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                        Toast.makeText(mainActivity, "Sign in successful!", Toast.LENGTH_SHORT).show();
                        mainActivity.employee=response.body().get(0);
                        mainActivity.if_registered=true;
                        Toast.makeText(mainActivity, mainActivity.employee.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<List<Employee>> call, Throwable t) {
                        Toast.makeText(mainActivity, "Sign in failed", Toast.LENGTH_SHORT).show();
                        Toast.makeText(mainActivity, employee.toString(), Toast.LENGTH_SHORT).show();
                        mainActivity.changeFragment(mainActivity.authentication);
                    }
                });
                mainActivity.changeFragment(mainActivity.profile);
            }
        });
    }
}