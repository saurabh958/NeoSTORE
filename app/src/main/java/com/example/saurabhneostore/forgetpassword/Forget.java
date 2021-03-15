package com.example.saurabhneostore.forgetpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.viewmodel.ForgetViewModel;
import com.example.saurabhneostore.viewmodel.ForgetViewModelFactory;

public class Forget extends AppCompatActivity
{
    EditText ed1;
    Button submit;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ImageButton imageButton;
    ForgetViewModel forgetViewModel;
    String emaildata;


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


        forgetViewModel = new ViewModelProvider(this, new ForgetViewModelFactory(this)).get(ForgetViewModel.class);
        forgetViewModel.getForgetListObserver().observe(this, new Observer<LoginmModelz>() {
            @Override
            public void onChanged(LoginmModelz loginmModelz) {
                if(loginmModelz!=null)
                {

                    Intent intent=new Intent(Forget.this, Login.class);
                    startActivity(intent);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emaildata=ed1.getText().toString();
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
                    forgetViewModel.forgetapicall(emaildata);

                }
            }
        });


    }

}