package com.example.saurabhneostore.forgetpassword;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.saurabhneostore.Login.Apisuccess;
import com.example.saurabhneostore.Login.JsonApi;
import com.example.saurabhneostore.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Forget extends AppCompatActivity
{
    EditText ed1;
    Button submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    JsonApi forgetapi;
    ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.forgetpass);


//        Toolbar toolbar=findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Forget Password");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        imageButton=findViewById(R.id.backbutton);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Forget.super.onBackPressed();
//            }
//        });

        ed1=findViewById(R.id.emailid);
        submit=findViewById(R.id.Submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed1.getText().toString().isEmpty()|| !ed1.getText().toString().matches(emailPattern))
                {
                    if(ed1.getText().toString().isEmpty())
                    {
                        ed1.setError("Enter Email Id");
                        ed1.requestFocus();
                    }
                    if(!ed1.getText().toString().matches(emailPattern))
                    {
                        ed1.setError("Enter Valid Email Id");
                        ed1.requestFocus();
                    }
                }
                else
                {
                    String emltxt=ed1.getText().toString();
                    Retrofit retrofit=new Retrofit.Builder()
                            .baseUrl("http://staging.php-dev.in:8844/trainingapp/api/users/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    forgetapi=retrofit.create(JsonApi.class);
                    //ForgetModel forgetModel=new ForgetModel(emltxt);
                    Call<Apisuccess>forgetModelCall=forgetapi.forgetdata(emltxt);
                    forgetModelCall.enqueue(new Callback<Apisuccess>() {
                        @Override
                        public void onResponse(Call<Apisuccess> call, Response<Apisuccess> response)
                        {
                            if(response.isSuccessful()){

                                Toast.makeText(Forget.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(
                                            Forget.this,
                                            jObjError.getString("user_msg"),
                                            Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(Forget.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                }
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}