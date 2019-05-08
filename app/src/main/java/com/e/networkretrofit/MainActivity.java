package com.e.networkretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.e.networkretrofit.models.Employee;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView name, age, salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name1);
        age = findViewById(R.id.age1);
        salary = findViewById(R.id.salary1);

        RetBuild();
    }


    public void RetBuild(){
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

        Call<List<Employee>> call = employeeClient.getEmployees();

        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                Log.d("val", response.body().toString() );

                List<Employee> employees = response.body();

                for(Employee emp : employees)
                {
//                    Log.d("Emp name ", emp.getName());

                    name.setText(emp.getEmployee_name());
                    age.setText(emp.getEmployee_age());
                    salary.setText(emp.getEmployee_salary());
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Log.d("val", t.getLocalizedMessage() );

            }
        });
    }
}
