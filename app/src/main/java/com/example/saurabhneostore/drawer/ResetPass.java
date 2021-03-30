package com.example.saurabhneostore.drawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.viewmodel.ResetPassVIewModelFactory;
import com.example.saurabhneostore.viewmodel.ResetPassViewModel;

public class ResetPass extends AppCompatActivity
{
    EditText currentpass,newpass,confpass;
    public static Button reset;
    ImageButton imageButton;
    ResetPassViewModel resetPassViewModel;
    public static ProgressBar rstprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_pass);

        currentpass=findViewById(R.id.rstcurrentpass);
        newpass=findViewById(R.id.rstnewpass);
        confpass=findViewById(R.id.rstconfrmpass);

        reset=findViewById(R.id.resetpassbutton);
        imageButton=findViewById(R.id.rstpassbackbutton);
        rstprogressBar=findViewById(R.id.reset_progress_bar);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPass.super.onBackPressed();
            }
        });

        resetPassViewModel=new ViewModelProvider(this,new ResetPassVIewModelFactory(this)).get(ResetPassViewModel.class);
        resetPassViewModel.getResetpasswrdlistObserver().observe(this, new Observer<LoginmModelz>() {
            @Override
            public void onChanged(LoginmModelz loginmModelz) {
                Log.d("resetpass","in onchanged (data changed success)");

                SharedPreferences sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
                SharedPreferences.Editor myedit=sp.edit();
                myedit.putString("FName","");
                myedit.clear();
                myedit.commit();
                Intent intent=new Intent(ResetPass.this,Login.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Toast.makeText(ResetPass.this,"Please Login Again",Toast.LENGTH_LONG).show();
                startActivity(intent);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curnt=currentpass.getText().toString();
                String newp=newpass.getText().toString();
                String confp=confpass.getText().toString();
                if(currentpass.getText().toString().isEmpty()||newpass.getText().toString().isEmpty()||
                        confpass.getText().toString().isEmpty()|| newp.equals(curnt)||!newp.equals(confp)||
                        newpass.getText().toString().length()>0 && newpass.getText().toString().length()<6)
                {
                    if(curnt.isEmpty())
                    {
                        currentpass.requestFocus();
                        currentpass.setError("Please Enter Current Password");
                        return;
                    }
                    if(newp.isEmpty())
                    {
                        newpass.requestFocus();
                        newpass.setError("Please Enter New Password");
                        return;
                    }
                    if(confp.isEmpty())
                    {
                        confpass.requestFocus();
                        confpass.setError("Please Enter Confirm Password");
                        return;
                    }
                    if(newp.equals(curnt))
                    {
                        newpass.requestFocus();
                        newpass.setError("Password is same as old password");
                        return;
                    }
                    if(!newp.equals(confp))
                    {
                        confpass.requestFocus();
                        confpass.setError("Confirm Password is not same as Password");
                    }
                    if(newpass.getText().toString().length()>0 && newpass.getText().toString().length()<6)
                    {
                        newpass.requestFocus();
                        newpass.setError("Minimum Length of Password Should be 6");

                    }
                }
                else
                {
                    Log.d("resetpass","in else part begin to hit api");
                    String curnt1=currentpass.getText().toString();
                    String newp1=newpass.getText().toString();
                    String confp1=confpass.getText().toString();
                    SharedPreferences sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
                    String token=sp.getString("Access","");
                    Log.d("resetpass",token);
                    reset.setVisibility(View.GONE);
                    resetPassViewModel.EditPasswordApiCall(token,curnt1,newp1,confp1);
                    rstprogressBar.setVisibility(View.VISIBLE);
                    Log.d("resetpass","api called");


                }
            }
        });




    }
}
