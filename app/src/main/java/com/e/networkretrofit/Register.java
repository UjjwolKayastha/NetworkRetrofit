package com.e.networkretrofit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.networkretrofit.models.EmpCU;
import com.e.networkretrofit.models.Employee;

import java.io.ByteArrayOutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    private final static String BASE_URL = "http://dummy.restapiexample.com/api/v1/";
    private static final String TAG = Register.class.getSimpleName();
    EditText etName, etAge, etSalary;
    Button btnRegister, upload, update;

    TextView tvname, tvage, tvsalary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etSalary = findViewById(R.id.etSalary);

        tvname = findViewById(R.id.tvName);
        tvage = findViewById(R.id.tvAge);
        tvsalary = findViewById(R.id.tvSalary);

        btnRegister = findViewById(R.id.btnRegister);
        upload = findViewById(R.id.btnUpload);
        update = findViewById(R.id.UpdateActivity);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Update.class));
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto();
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }

            private void Register() {
                final String name = etName.getText().toString();
                String salary = (etSalary.getText().toString());
                String age = (etAge.getText().toString());


                EmpCU employee = new EmpCU(name, salary, age);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                EmployeeClient employeeClient = retrofit.create(EmployeeClient.class);

                Call<ResponseBody> call = employeeClient.registerEmployee(employee);


                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(!response.isSuccessful())
                        {
                            Toast.makeText(Register.this, "err REGISTERED", Toast.LENGTH_SHORT).show();
                        }

                        Log.d("hello", response.toString());
                        Toast.makeText(Register.this, "SUCCESSFULLY REGISTERED", Toast.LENGTH_SHORT).show();

                        Log.d("val", "onResponse: " + response.toString());

                        tvname.setText(etName.getText().toString());
                        tvage.setText(etAge.getText().toString());
                        tvsalary.setText(etSalary.getText().toString());


                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(Register.this, "Error :" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }

    public void uploadPhoto() {
        final String BASE_URL = "https://api.imgur.com/";
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL);

        Retrofit retrofit = builder.client(httpClient.build()).build();


        Bitmap bitmap = BitmapFactory.decodeResource(Register.this.getResources(), R.drawable.ujjwol);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bytes = stream.toByteArray();

        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "image",    /* type */
                "ujjwol",    /*name of image file */
                RequestBody.create(MediaType.parse("image/*"),  /* media type */
                        bytes    /* actual content which is image here*/
                )
        );


        EmployeeClient employeeClient = retrofit.create(EmployeeClient.class);

        Call<ResponseBody> call = employeeClient.upload("Client-ID 6803e8b51da1ab7", body);


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                Log.d(TAG, response.toString());

                Log.d("val", "onResponse: IMAGE UPLOADED");

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Register.this, "Error :" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
