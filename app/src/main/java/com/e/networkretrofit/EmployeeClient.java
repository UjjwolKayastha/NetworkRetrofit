package com.e.networkretrofit;

import com.e.networkretrofit.models.EmpCU;
import com.e.networkretrofit.models.Employee;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface EmployeeClient {
    @Headers("Content-Type: application/json")
    //get annotate gareko ani "employees" chai endpoint
    @GET("employees")
    Call<List<Employee>> getEmployees();

    @GET("employee/{id}")
    Call<Employee> getEmployessById(
            @Path("id") int id
    );

    @POST("create")
    Call<ResponseBody> registerEmployee (@Body EmpCU employee);

    @Multipart
    @POST("3/upload")
    Call<ResponseBody> upload(
            @Header("Authorization") String clientId,
            @Part() MultipartBody.Part file
    );

    @PUT("update/{id}")
    Call<ResponseBody> updateEmployee(
            @Path("id") String id,
            @Body EmpCU empCU
    );

    @DELETE("delete/{id}")
    Call<ResponseBody> deleteEmployee(
            @Path("id") String id
    );


}

