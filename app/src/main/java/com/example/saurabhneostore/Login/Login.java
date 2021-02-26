package com.example.saurabhneostore.Login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.Register.Register;
import com.example.saurabhneostore.forgetpassword.Forget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity
{
    FloatingActionButton flt1;
    Button login;
    EditText usern,pass;
    TextView tv1;
    JsonApi jsonApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_neo);
        //getSupportActionBar().setTitle("Login");

        flt1=findViewById(R.id.floatng);
        login=findViewById(R.id.login);
        usern=findViewById(R.id.username);
        pass=findViewById(R.id.passwrd);
        tv1=findViewById(R.id.forgo);

        flt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(usern.getText().toString().isEmpty()||pass.getText().toString().isEmpty())
                {
                    if (usern.getText().toString().isEmpty())
                    {
                        usern.requestFocus();
                        usern.setError("Please Enter username");

                    }
                    if (pass.getText().toString().isEmpty())
                    {
                        pass.requestFocus();
                        pass.setError("Please enter Password");
                    }
                }
                else
                {
                    String ed1=usern.getText().toString();
                    String ed2=pass.getText().toString();
                    Retrofit retrofit=new Retrofit.Builder()
                            .baseUrl("http://staging.php-dev.in:8844/trainingapp/api/users/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    jsonApi=retrofit.create(JsonApi.class);
//                    LoginModel loginModel=new LoginModel(ed1,ed2);
                    Call<Apisuccess> call=jsonApi.checkdata(ed1,ed2);
                    call.enqueue(new Callback<Apisuccess>() {
                        @Override
                        public void onResponse(Call<Apisuccess> call, Response<Apisuccess> response)
                        {
                            if(response.isSuccessful()){

                                Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                //startActivity(new Intent(Login.this, Homepage.class));
                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(
                                            Login.this,
                                            jObjError.getString("user_msg"),
                                            Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }


                        }

                        @Override
                        public void onFailure(Call<Apisuccess> call, Throwable t)
                        {
                            Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();

                            System.out.println("-----"+t.getMessage());
                        }
                    });
//                    Toast.makeText(getApplicationContext(),"Entered data is"+usern.getText().toString()+" "
//                            +pass.getText().toString(),Toast.LENGTH_LONG).show();
                }

            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Forget.class));
            }
        });
    }
}