package com.example.saurabhneostore.drawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccount extends AppCompatActivity {
    Toolbar toolbar;
    EditText firstname,lastname,email,mob,dob;
    Button editbtn,reset;
    CircleImageView imageView;
    ImageButton backbutton;
    TextView initial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);

        backbutton=findViewById(R.id.myaccntbackbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAccount.super.onBackPressed();
            }
        });

        firstname=findViewById(R.id.myaccntfirstname);
        lastname=findViewById(R.id.myaccntlastname);
        email=findViewById(R.id.myaccntemail);
        mob=findViewById(R.id.myaccntphonenumber);
        dob=findViewById(R.id.myaccntdob);
        initial=findViewById(R.id.myaccnt_initial);

        editbtn=findViewById(R.id.myaccnteditprofilebtn);
        reset=findViewById(R.id.rstpasswrdbutton);

        imageView=findViewById(R.id.myaccntimage);

        SharedPreferences sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        firstname.setText(sp.getString("FName",""));
        firstname.setEnabled(false);

        lastname.setText(sp.getString("LName",""));
        lastname.setEnabled(false);

        email.setText(sp.getString("Email",""));
        email.setEnabled(false);

        mob.setText(sp.getString("Phone",""));
        mob.setEnabled(false);

        dob.setText(sp.getString("Bday",""));
        dob.setEnabled(false);

        String image=sp.getString("Pic","");
        Log.d("pintu","prefs - string image"+image);
        if(!image.equals("null"))
        {
            Picasso.with(getApplicationContext())
                    .load(image)
                    .fit()
                    .into(imageView);
            initial.setVisibility(View.INVISIBLE);
        }
        else
        {
            initial.setVisibility(View.VISIBLE);
            String fini=(sp.getString("FName","")).toUpperCase();
            String lini=(sp.getString("LName","")).toUpperCase();
            String initials=fini.substring(0,1)+lini.substring(0,1);

            initial.setText(initials);
        }



        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyAccount.this,EditProfile.class);
                startActivityForResult(intent,2);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyAccount.this,ResetPass.class);

                startActivity(intent);
            }
        });



        





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            SharedPreferences sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
            firstname.setText(sp.getString("FName",""));
            firstname.setEnabled(false);

            lastname.setText(sp.getString("LName",""));
            lastname.setEnabled(false);

            email.setText(sp.getString("Email",""));
            email.setEnabled(false);

            mob.setText(sp.getString("Phone",""));
            mob.setEnabled(false);

            dob.setText(sp.getString("Bday",""));
            dob.setEnabled(false);

            String image=sp.getString("Pic","");
            Log.d("pintu","prefs - string image"+image);
            if(!image.equals("null"))
            {
                Picasso.with(getApplicationContext())
                        .load(image)
                        .fit()
                        .into(imageView);
                initial.setVisibility(View.INVISIBLE);

            }
            else
            {
                initial.setVisibility(View.VISIBLE);
                String fini=(sp.getString("FName","")).toUpperCase();
                String lini=(sp.getString("LName","")).toUpperCase();
                String initials=fini.substring(0,1)+lini.substring(0,1);

                initial.setText(initials);
            }

        }
    }
}