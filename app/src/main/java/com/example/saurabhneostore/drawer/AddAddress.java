package com.example.saurabhneostore.drawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    Button place_order;
    OrderViewModel orderViewModel;
    public static SharedPreferences sp;



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

        place_order=findViewById(R.id.btn_place_order);



        sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        String token=sp.getString("Access","");


        orderViewModel=new ViewModelProvider(this,new OrderViewModelFactory(this)).get(OrderViewModel.class);
        orderViewModel.getOrderMutableObserver().observe(this, new Observer<OrderModel>() {
            @Override
            public void onChanged(OrderModel orderModel) {
                if(orderModel!=null)
                {
                    Log.d("annu","in onchanged of addaddress ");
                    MyCart.myCartViewModel.loadcart(token);
                }
            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addrss=add.getText().toString();
                orderViewModel.makeordercall(token,addrss);

            }
        });
    }
}
