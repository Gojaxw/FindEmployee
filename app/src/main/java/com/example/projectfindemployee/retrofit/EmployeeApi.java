package com.example.projectfindemployee.retrofit;

import com.example.projectfindemployee.model.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface EmployeeApi {
    @GET("/employee/get-all")
    Call<List<Employee>> getAllEmployee();
    @POST("/employee/signin")
    Call<List<Employee>> getEmployee(@Body Employee employee);
    @POST("/employee/save")
    Call<Employee> save(@Body Employee employee);
    @PATCH("/employee/change")
    Call<Employee> change(@Body Employee employee);
    @GET("/employee/get{1}")
    Call<List<Employee>> getEmployeeCoordinate();


}
