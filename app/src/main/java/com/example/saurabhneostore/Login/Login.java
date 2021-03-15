package com.example.saurabhneostore.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.Register.Register;
import com.example.saurabhneostore.forgetpassword.Forget;
import com.example.saurabhneostore.homepage.Homepage;
import com.example.saurabhneostore.model.LoginmModelz;
import com.example.saurabhneostore.viewmodel.LoginViewModel;
import com.example.saurabhneostore.viewmodel.LoginViewModelFactory;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Login extends AppCompatActivity
{
    FloatingActionButton flt1;
    Button login;
    EditText usern,pass;
    TextView tv1;
    private LoginViewModel loginViewModel;
    public static final String PREFS_NAME = "MySharedPref";




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



        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this)).get(LoginViewModel.class);
        loginViewModel.getLoginListObserver().observe(this, new Observer<LoginmModelz>() {
            @Override
            public void onChanged(LoginmModelz loginmModelz) {
                if(loginmModelz!=null)
                {
                    String F = loginmModelz.getData().getFirstName();
                    String L = loginmModelz.getData().getLastName();
                    String U = loginmModelz.getData().getUsername();
                    String E = loginmModelz.getData().getEmail();
                    String G = loginmModelz.getData().getGender();
                    String P = loginmModelz.getData().getPhoneNo();
                    String A =loginmModelz.getData().getAccessToken();
                    String bday= String.valueOf(loginmModelz.getData().getDob());
                    String photo= String.valueOf(loginmModelz.getData().getProfilePic());
                    Log.d("pintu","Login - photo "+photo);

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

                    Intent intent=new Intent(Login.this, Homepage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });




        flt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
                Log.d("main","float is pressed");
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String ed1=usern.getText().toString();
                String ed2=pass.getText().toString();
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

                    loginViewModel.makeApiCall(ed1,ed2);


//                    loginNeoBinding= DataBindingUtil.setContentView(Login.this,R.layout.login_neo);
//                    loginViewModel=new LoginViewModel(Login.this);
//                    loginNeoBinding.setLoginview(loginViewModel);
//                    Log.d("main","inside else part");
//
//
//                    loginNeoBinding.setPresenter(new Presenter() {
//                        @Override
//                        public void logindata() {
//
//                            final String name=loginNeoBinding.username.getText().toString();
//                            final String pass=loginNeoBinding.passwrd.getText().toString();
//
//                            loginViewModel.sendloginrequest(name,pass);
//                            Log.d("main","inside logindata function");
//
//                        }
//                    });

//
                    Log.d("main","last line of else");
                }


            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Forget.class));
                Log.d("main","forget is clicked");
            }
        });

    }


}