package com.example.saurabhneostore.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.Register.Register;
import com.example.saurabhneostore.forgetpassword.Forget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Login extends AppCompatActivity
{
    FloatingActionButton flt1;
    Button login;
    EditText usern,pass;
    TextView tv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_neo);
        getSupportActionBar().setTitle("Login");

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
                    Toast.makeText(getApplicationContext(),"Entered data is"+usern.getText().toString()+" "
                            +pass.getText().toString(),Toast.LENGTH_LONG).show();
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