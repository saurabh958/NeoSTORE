package com.example.saurabhneostore.drawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.example.saurabhneostore.model.Order.OrderModel;
import com.example.saurabhneostore.viewmodel.OrderViewModel;
import com.example.saurabhneostore.viewmodel.OrderViewModelFactory;

public class AddAddress extends AppCompatActivity
{
    EditText add,city,landmark,zip,state,country;
    public static Button place_order;
    OrderViewModel orderViewModel;
    public static SharedPreferences sp;
    ImageButton back;
    public static ProgressBar add_progress;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address);

        add=findViewById(R.id.ed_address);
        city=findViewById(R.id.ed_city);
        landmark=findViewById(R.id.ed_landmark);
        zip=findViewById(R.id.ed_zip);
        state=findViewById(R.id.ed_state);
        country=findViewById(R.id.ed_country);
        add_progress=findViewById(R.id.address_progress_bar);
        back=findViewById(R.id.add_backbutton);

        place_order=findViewById(R.id.btn_place_order);



        sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        String token=sp.getString("Access","");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddAddress.super.onBackPressed();
            }
        });


        orderViewModel=new ViewModelProvider(this,new OrderViewModelFactory(this)).get(OrderViewModel.class);
        orderViewModel.getOrderMutableObserver().observe(this, new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
                if(orderModel!=null)
                {
                    Log.d("annu","in onchanged of addaddress ");
                    MyCart.myCartViewModel.loadcart(token);
                    finish();
                }
            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addrss=add.getText().toString();



                        String Address = add.getText().toString().trim();
                        String landmarks = landmark.getText().toString().trim();
                        String citys = city.getText().toString().trim();
                        String states = state.getText().toString().trim();
                        String zips = zip.getText().toString().trim();
                        String countrys = country.getText().toString().trim();

                        if(add.length()==0 || landmark.length()==0 || city.length()==0 || zip.length()==0 || country.length()==0){

                            if(Address.length()==0){
                                add.requestFocus();
                                add.setError("FIELD CANNOT BE EMPTY");
                            }
                            if(landmarks.length()==0){
                                landmark.requestFocus();
                                landmark.setError("FIELD CANNOT BE EMPTY");
                            }
                            if(citys.length()==0){
                                city.requestFocus();
                                city.setError("FIELD CANNOT BE EMPTY");
                            }
                            if(states.length()==0){
                                state.requestFocus();
                                state.setError("FIELD CANNOT BE EMPTY");
                            }
                            if(zips.length()==0){
                                zip.requestFocus();
                                zip.setError("FIELD CANNOT BE EMPTY");
                            }
                            if(countrys.length()==0){
                                country.requestFocus();
                                country.setError("FIELD CANNOT BE EMPTY");
                            }

                        }else{
                            place_order.setVisibility(View.GONE);
                            orderViewModel.makeordercall(token,add.getText().toString());
                            add_progress.setVisibility(View.VISIBLE);
                        }
            }
        });
    }
}
