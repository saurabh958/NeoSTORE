package com.example.saurabhneostore.forgetpassword;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.saurabhneostore.R;

public class Forget extends AppCompatActivity
{
    EditText ed1;
    Button submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpass);
        getSupportActionBar().setTitle("Forget Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        return;
                    }
                    if(!ed1.getText().toString().matches(emailPattern))
                    {
                        ed1.setError("Enter Valid Email Id");
                        ed1.requestFocus();
                        return;
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Email Sent",Toast.LENGTH_LONG).show();
                    finish();
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