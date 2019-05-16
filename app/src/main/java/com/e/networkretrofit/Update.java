package com.e.networkretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.networkretrofit.models.EmpCU;
import com.e.networkretrofit.models.Employee;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Update extends AppCompatActivity {

    EditText name, age, salary, id;
    Button btnshow, btnUpdate, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        id = findViewById(R.id.id);
        name = findViewById(R.id.updateName);
        age = findViewById(R.id.updateAge);
        salary = findViewById(R.id.updateSalary);

        btnshow = findViewById(R.id.btnshowUpdate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetBuildGetById();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee();
            }
        });

    }

    public void RetBuildGetById() {
        String API_BASE_URL = "http://dummy.restapiexample.com/api/v1/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit = builder.client(httpClient.build()).build();

        EmployeeClient employeeClient = retrofit.create(EmployeeClient.class);

        Call<Employee> call = employeeClient.getEmployessById(Integer.parseInt(id.getText().toString()));

        call.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {


                Log.d("val", response.body().toString());

                name.setText(response.body().getEmployee_name());
                age.setText(response.body().getEmployee_age());
                salary.setText(response.body().getEmployee_salary());

            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Log.d("val", t.getLocalizedMessage());

            }


        });
    }

    public void updateEmployee(){
        String API_BASE_URL = "http://dummy.restapiexample.com/api/v1/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit = builder.client(httpClient.build()).build();

        EmployeeClient employeeClient = retrofit.create(EmployeeClient.class);

        EmpCU empCU = new EmpCU(name.getText().toString(), salary.getText().toString(), age.getText().toString());

        Call<ResponseBody> call = employeeClient.updateEmployee(id.getText().toString(), empCU);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(Update.this, "UPDATE SUCCESSFUL", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Update.this, "UPDATE SUCCESSFUL", Toast.LENGTH_LONG).show();

            }
        });
    }


    public void deleteEmployee(){
        String API_BASE_URL = "http://dummy.restapiexample.com/api/v1/";

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(API_BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit = builder.client(httpClient.build()).build();

        EmployeeClient employeeClient = retrofit.create(EmployeeClient.class);


        Call<ResponseBody> call = employeeClient.deleteEmployee(id.getText().toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(Update.this, "USER DELETION SUCCESSFUL", Toast.LENGTH_LONG).show();

                id.setText("");
                name.setText("");
                age.setText("");
                salary.setText("");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Update.this, "UPDATE SUCCESSFUL", Toast.LENGTH_LONG).show();

            }
        });
    }
}
