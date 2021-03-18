package com.example.saurabhneostore.drawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.example.saurabhneostore.adapters.MyCartAdapter;
import com.example.saurabhneostore.model.MyCart.Datum;
import com.example.saurabhneostore.model.MyCart.MyCartModel;
import com.example.saurabhneostore.model.QuantityModel;
import com.example.saurabhneostore.viewmodel.DeleteCartItemViewModel;
import com.example.saurabhneostore.viewmodel.DeleteCartViewModelFactory;
import com.example.saurabhneostore.viewmodel.MyCartViewModel;
import com.example.saurabhneostore.viewmodel.MyCartViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class MyCart extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView total;
    Button order;
    MyCartViewModel myCartViewModel;
    ImageButton backcutton;
    List<Datum> cartlist=new ArrayList<>();
    MyCartAdapter myCartAdapter;
    public static DeleteCartItemViewModel deleteCartItemViewModel;
    public static SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);

        total=findViewById(R.id.mycart_price);
        order=findViewById(R.id.mycart_ordernowbtn);
        recyclerView=findViewById(R.id.mycart_recycler);
        backcutton=findViewById(R.id.mycart_backbutton);

        backcutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCart.super.onBackPressed();
            }
        });

        Log.d("annu","in on create");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        String token=sp.getString("Access","");





        deleteCartItemViewModel=new ViewModelProvider(this, new DeleteCartViewModelFactory(this)).get(DeleteCartItemViewModel.class);
        deleteCartItemViewModel.getDeletecartlivedata().observe(this, new Observer<QuantityModel>() {
            @Override
            public void onChanged(QuantityModel quantityModel) {
                if(quantityModel!=null)
                {
                    Log.d("annu","in onchenged method deletecart");
                    myCartAdapter=new MyCartAdapter(MyCart.this,cartlist);
                    recyclerView.setAdapter(myCartAdapter);

                    myCartAdapter.notifyDataSetChanged();
                }
            }
        });




        myCartViewModel=new ViewModelProvider(this,new MyCartViewModelFactory(this)).get(MyCartViewModel.class);
        myCartViewModel.getCartDataLiveData().observe(this, new Observer<MyCartModel>() {
            @Override
            public void onChanged(MyCartModel myCartModel) {
                if(myCartModel!=null)
                {

                    Log.d("annu","in onchanged ");


                    total.setText("₹"+myCartModel.getTotal());

                    cartlist=myCartModel.getData();
                    myCartAdapter=new MyCartAdapter(MyCart.this,cartlist);
                    recyclerView.setAdapter(myCartAdapter);
                    Log.d("annu","adapter set");






                }
            }
        });

        Log.d("annu",token+"token data");

        myCartViewModel.loadcart(token);
    }
}