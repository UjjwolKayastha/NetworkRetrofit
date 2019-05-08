package com.e.networkretrofit;

import com.e.networkretrofit.models.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeClient {
    //get annotate gareko ani "employees" chai endpoint
    @GET("employees")
    Call<List<Employee>> getEmployees();
}

