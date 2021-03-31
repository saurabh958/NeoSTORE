package com.example.saurabhneostore.drawer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.viewmodel.EditProfileViewModel;
import com.example.saurabhneostore.viewmodel.EditProfileViewModelFactory;
import com.facebook.stetho.Stetho;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity
{
    EditText fname,lname,email,phone,dob;
    CircleImageView img1;
    public static Button submit;
    ImageButton imageButton;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    EditProfileViewModel editProfileViewModel;
    private static final int SELECT_PICTURE = 0;
    private static int RESULT_LOAD_IMAGE = 1;
    Bitmap bitmap;
    String bitprofile="";
    DatePickerDialog datePickerDialog;
    public static final String PREFS_NAME = "MySharedPref";
    public static ProgressBar progressBar;
    TextView nameiniti;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.edit_profile);

        fname=findViewById(R.id.editprofilefirstname);
        lname=findViewById(R.id.editprofilelastname);
        email=findViewById(R.id.editprofileemail);
        phone=findViewById(R.id.editprofilephonenumber);
        dob=findViewById(R.id.editprofiledob);
        img1=findViewById(R.id.editprofileimage);
        submit=findViewById(R.id.editprofileeditprofilebtn);
        progressBar=findViewById(R.id.editprofile_progress_bar);
        nameiniti=findViewById(R.id.edit_initial);

        imageButton=findViewById(R.id.editprofilebackbutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfile.super.onBackPressed();
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

                datePickerDialog=new DatePickerDialog(EditProfile.this,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
                    }
                },mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });


        Log.d("editprofile","in oncreate");
        SharedPreferences sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        fname.setText(sp.getString("FName",""));
        lname.setText(sp.getString("LName",""));
        email.setText(sp.getString("Email",""));
        phone.setText(sp.getString("Phone",""));
        dob.setText(sp.getString("Bday",""));


        String image1=sp.getString("Pic","");
        Log.d("annu","image 1 data is "+image1);
        if(!image1.equals("null"))
        {
            Log.d("annu","if null wala");
            Picasso.with(getApplicationContext())
                    .load(image1)
                    .fit()
                    .into(img1);
            nameiniti.setVisibility(View.INVISIBLE);

        }
        else
        {
            nameiniti.setVisibility(View.VISIBLE);
            String fini=(sp.getString("FName","")).toUpperCase();
            String lini=(sp.getString("LName","")).toUpperCase();
            String initials=fini.substring(0,1)+lini.substring(0,1);

            nameiniti.setText(initials);



        }







        Log.d("pintu","prefs - string image"+image1);




        editProfileViewModel= new ViewModelProvider(this, new EditProfileViewModelFactory(this)).get(EditProfileViewModel.class);
        editProfileViewModel.getEditprofilelistObserver().observe(this, new Observer<LoginmModelz>() {
            @Override
            public void onChanged(LoginmModelz loginmModelz) {

                //Toast.makeText(EditProfile.this,"Data changed successfully",Toast.LENGTH_LONG).show();

                String F = loginmModelz.getData().getFirstName();
                String L = loginmModelz.getData().getLastName();
                String U = loginmModelz.getData().getUsername();
                String E = loginmModelz.getData().getEmail();
                String G = loginmModelz.getData().getGender();
                String P = loginmModelz.getData().getPhoneNo();
                String A =loginmModelz.getData().getAccessToken();
                String bday= String.valueOf(loginmModelz.getData().getDob());
                String photo= String.valueOf(loginmModelz.getData().getProfilePic());

                SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();
                myEdit.putString("FName", F.toUpperCase());
                myEdit.putString("LName", L.toUpperCase());
                myEdit.putString("UName", U);
                myEdit.putString("Email", E);
                myEdit.putString("Gender", G);
                myEdit.putString("Phone", P);
//                    myEdit.putBoolean("hasloggedin",true);
                myEdit.putString("Access",A);
                myEdit.putString("Bday",bday);
                myEdit.putString("Pic",photo);
                myEdit.commit();



                Log.d("editprofile","in onchanged (data changed success)");
                Intent intent=new Intent(EditProfile.this,MyAccount.class);
                setResult(2,intent);
                //startActivity(intent);
                finish();

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fname.getText().toString().isEmpty()||lname.getText().toString().isEmpty()||email.getText().toString().isEmpty()||
                        phone.getText().toString().isEmpty()||dob.getText().toString().isEmpty()||
                        !fname.getText().toString().matches("[a-zA-Z ]+")||
                        !lname.getText().toString().matches("[a-zA-Z ]+")||
                        !email.getText().toString().matches(emailPattern))
                {
                    if(fname.getText().toString().isEmpty())
                    {
                        fname.requestFocus();
                        fname.setError("Please Enter First Name");

                    }
                    else if(!fname.getText().toString().matches("[a-zA-Z ]+"))
                    {
                        fname.requestFocus();
                        fname.setError("Please Enter Alphabets");

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


                    if(phone.getText().toString().isEmpty())
                    {
                        phone.requestFocus();
                        phone.setError("Please Enter Phone Number");

                    }
                    else if(phone.length()<10)
                    {
                        phone.requestFocus();
                        phone.setError("Enter Valid Phone Number");

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

                    if(dob.getText().toString().isEmpty())
                    {
                        dob.requestFocus();
                        dob.setError("enter Birthday");
                    }
                   

                }
                else
                {

                    String Fname=fname.getText().toString();
                    String Lname=lname.getText().toString();
                    String Email=email.getText().toString();
                    String Phone=phone.getText().toString();
                    String Dob=dob.getText().toString();
                    SharedPreferences sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
                    String token=sp.getString("Access","");
                    Log.d("editprofile",token);
                    Log.d("annu",bitprofile);

                    if(bitprofile.equals(""))
                    {
                        bitprofile="";
                        submit.setVisibility(View.GONE);
                        Log.d("annu","if null");
                        editProfileViewModel.editApiCall(token,Fname,Lname,Email,Dob,bitprofile,Phone);
                        progressBar.setVisibility(View.VISIBLE);
                    }
                    else
                    {

                        submit.setVisibility(View.GONE);
                        Log.d("annu","else null");
                        Log.d("annu","bitdata is :-> "+bitprofile);
                        editProfileViewModel.editApiCall(token,Fname,Lname,Email,Dob,bitprofile,Phone);
                        progressBar.setVisibility(View.VISIBLE);
                    }


                    Log.d("editprofile","api called");



                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                img1 = findViewById(R.id.editprofileimage);
                img1.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
            bitprofile = Base64.encodeToString(imgByte, Base64.DEFAULT);
            bitprofile = "data:image/jpg;base64," + bitprofile;
            nameiniti.setVisibility(View.INVISIBLE);
        }
    }



}
