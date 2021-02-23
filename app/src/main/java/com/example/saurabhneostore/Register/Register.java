package com.example.saurabhneostore.Register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.saurabhneostore.Login.Apisuccess;
import com.example.saurabhneostore.R;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Register extends AppCompatActivity
{
    EditText name,lname,email,pass,cpass,mob;
    Button regist;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    RadioGroup rgp1;
    RadioButton maler,femaler;
    TextView inv1;
    JsonPlaceHolderApi jsonPlaceHolderApi;
    String gendr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name=findViewById(R.id.firstname);
        lname=findViewById(R.id.lastname);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        cpass=findViewById(R.id.confrmpasswrd);
        mob=findViewById(R.id.phonenumber);
        regist=findViewById(R.id.button);
        rgp1=findViewById(R.id.radioGroup);
        maler=findViewById(R.id.male);
        femaler=findViewById(R.id.female);
        inv1=findViewById(R.id.inv);



        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String phn=mob.getText().toString();
                int selectmale=rgp1.getCheckedRadioButtonId();
                class Radgen implements CompoundButton.OnCheckedChangeListener{

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if(maler.isChecked())
                        {
                            inv1.setVisibility(View.INVISIBLE);
                            gendr=maler.getText().toString().substring(0,1);


                        }
                        else if(femaler.isChecked())
                        {
                            inv1.setVisibility(View.INVISIBLE);
                            gendr=femaler.getText().toString().substring(0,1);
                        }

                    }
                }




                if(name.getText().toString().isEmpty()||lname.getText().toString().isEmpty()||
                        email.getText().toString().isEmpty()||pass.getText().toString().isEmpty()||
                        cpass.getText().toString().isEmpty()|| mob.getText().toString().isEmpty()||
                        !name.getText().toString().matches("[a-zA-Z ]+")||
                        !lname.getText().toString().matches("[a-zA-Z ]+")||
                        mob.length()<10 || !email.getText().toString().matches(emailPattern) ||
                        !cpass.getText().toString().matches(pass.getText().toString()))
                {
                    if(name.getText().toString().isEmpty())
                    {
                        name.requestFocus();
                        name.setError("Please Enter First Name");

                    }
                    else if(!name.getText().toString().matches("[a-zA-Z ]+"))
                    {
                        name.requestFocus();
                        name.setError("Please Enter Alphabets");

                    }



                    if(lname.getText().toString().isEmpty())
                    {
                        lname.requestFocus();
                        lname.setError("Please Enter Last Name");

                    }
                    else if(!lname.getText().toString().matches("[a-zA-Z ]+"))
                    {
                        lname.requestFocus();
                        lname.setError("Please Enter Alphabets");
                    }
                    if(selectmale==-1)
                    {
                        inv1.setVisibility(View.VISIBLE);
                        maler.setOnCheckedChangeListener(new Radgen());
                        femaler.setOnCheckedChangeListener(new Radgen());

                    }

                    if(mob.getText().toString().isEmpty())
                    {
                        mob.requestFocus();
                        mob.setError("Please Enter Phone Number");

                    }
                    else if(phn.length()<10)
                    {
                        mob.requestFocus();
                        mob.setError("Enter Valid Phone Number");

                    }

                    if (email.getText().toString().isEmpty())
                    {
                        email.requestFocus();
                        email.setError("Please Enter Email Id");
                    }
                    else if (!email.getText().toString().matches(emailPattern)) {
                        email.requestFocus();
                        email.setError("Please Enter Valid Email Id");

                    }
                    if(pass.getText().toString().isEmpty())
                    {
                        pass.requestFocus();
                        pass.setError("Enter Password");
                    }

                    if(cpass.getText().toString().isEmpty())
                    {
                        cpass.requestFocus();
                        cpass.setError("Enter Confirm Password");
                    }
                    if(!cpass.getText().toString().matches(pass.getText().toString()))
                    {
                        cpass.requestFocus();
                        cpass.setError("Please Enter Same Password as Above");
                    }
                }
                else
                {
                    //Toast.makeText(getApplicationContext(),"Registered Success",Toast.LENGTH_SHORT).show();
                    String ed1=name.getText().toString();
                    String ed2=lname.getText().toString();
                    String ed3=email.getText().toString();
                    String ed4=pass.getText().toString();
                    String ed5=cpass.getText().toString();
                    if(maler.isChecked())
                    {
                        gendr=maler.getText().toString().substring(0,1);

                    }
                    else if(femaler.isChecked())
                    {
                        gendr=femaler.getText().toString().substring(0,1);
                    }
                    System.out.println("gender is"+gendr+"phn is"+phn);
                    Retrofit retrofit=new Retrofit.Builder()
                            .baseUrl("http://staging.php-dev.in:8844/trainingapp/api/users/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    jsonPlaceHolderApi=retrofit.create(JsonPlaceHolderApi.class);
                    //RegisterModel registerModel=new RegisterModel(ed1,ed2,ed3,ed4,ed5,gendr,phn);

                    Call<Apisuccess> call=jsonPlaceHolderApi.createdata(ed1,ed2,ed3,ed4,ed5,gendr,phn);
                    call.enqueue(new Callback<Apisuccess>() {
                        @Override
                        public void onResponse(Call<Apisuccess> call, Response<Apisuccess> response)
                        {
                            if(response.isSuccessful()){

                                Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                try {
                                    JSONObject jObjError = new JSONObject(response.errorBody().string());
                                    Toast.makeText(
                                            Register.this,
                                            jObjError.getString("user_msg"),
                                            Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
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