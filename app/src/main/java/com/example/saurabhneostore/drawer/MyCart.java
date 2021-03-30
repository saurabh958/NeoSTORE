package com.example.saurabhneostore.drawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.example.saurabhneostore.viewmodel.EditCartViewModel;
import com.example.saurabhneostore.viewmodel.EditCartViewModelFactory;
import com.example.saurabhneostore.viewmodel.MyCartViewModel;
import com.example.saurabhneostore.viewmodel.MyCartViewModelFactory;

import java.util.List;

public class MyCart extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView total,mycarttot;
    LinearLayout empty_layout;
    public static Button order;
    public static MyCartViewModel myCartViewModel;
    ImageButton backcutton;
    List<Datum> cartlist;
    MyCartAdapter myCartAdapter;
    public static DeleteCartItemViewModel deleteCartItemViewModel;
    public static SharedPreferences sp;
    public static EditCartViewModel editCartViewModel;
    Boolean flag=true;
    public static ProgressBar mycartprogress;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycart);

        total=findViewById(R.id.mycart_price);
        order=findViewById(R.id.mycart_ordernowbtn);
        recyclerView=findViewById(R.id.mycart_recycler);
        backcutton=findViewById(R.id.mycart_backbutton);
        mycarttot=findViewById(R.id.mycart_total);
        empty_layout=findViewById(R.id.empty_layout);
        mycartprogress=findViewById(R.id.mycart_progress_bar);
        sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();


        backcutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCart.super.onBackPressed();
            }
        });

        Log.d("annu","in on create");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);


        String token=sp.getString("Access","");

        myCartViewModel=new ViewModelProvider(this,new MyCartViewModelFactory(this)).get(MyCartViewModel.class);
        myCartViewModel.getCartDataLiveData().observe(this, new Observer<MyCartModel>() {
            @Override
            public void onChanged(MyCartModel myCartModel) {
                Log.d("annu","7");
                cartlist=myCartModel.getData();
                if(myCartModel!=null)
                {
                    Log.d("annu","8");
                    if(cartlist!=null)
                    {
                        empty_layout.setVisibility(View.GONE);
                        Log.d("annu","9");
                        Log.d("annu","in onchanged ");
                        total.setText("₹"+myCartModel.getTotal());
                        myCartAdapter=new MyCartAdapter(MyCart.this,cartlist);
                        recyclerView.setAdapter(myCartAdapter);
                        editor.putString("cart",myCartModel.getCount().toString());
                        editor.commit();
                        //Toast.makeText(MyCart.this,"Data Loaded",Toast.LENGTH_SHORT).show();
                        Log.d("annu","adapter set");
                    }
                    else
                    {
                        Log.d("annu","empty cart");
                        myCartAdapter=new MyCartAdapter(MyCart.this,cartlist);
                        recyclerView.setAdapter(myCartAdapter);
                        editor.putString("cart","0");
                        editor.commit();


                        setGone();
                    }
                }
                else
                {
                    Log.d("annu","emty if condition");


                }
            }
        });

        editCartViewModel=new ViewModelProvider(this,new EditCartViewModelFactory(this)).get(EditCartViewModel.class);
        editCartViewModel.getEditcartlivedata().observe(this, new Observer<QuantityModel>() {
            @Override
            public void onChanged(QuantityModel quantityModel) {
                if(quantityModel!=null)
                {
                    Log.d("annu","in onchanged method editcart");
                    myCartViewModel.loadcart(token);
                    flag=false;
                }
            }
        });







        deleteCartItemViewModel=new ViewModelProvider(this, new DeleteCartViewModelFactory(this)).get(DeleteCartItemViewModel.class);
        deleteCartItemViewModel.getDeletecartlivedata().observe(this, new Observer<QuantityModel>() {
            @Override
            public void onChanged(QuantityModel quantityModel) {
                if(quantityModel!=null)
                {
                    Log.d("annu","3");
                    if(quantityModel.getTotalCarts()!=0)
                    {
                        Log.d("annu","4");
                        Log.d("annu","in onchenged method deletecart");
                        myCartViewModel.loadcart(token);
                        flag=false;
                    }
                    else
                    {

                        Log.d("annu","else of onchanged delete");
                        myCartViewModel.loadcart(token);
                        setGone();

                        //showemptycart
                    }

//                    myCartAdapter=new MyCartAdapter(MyCart.this,cartlist);
//                    recyclerView.setAdapter(myCartAdapter);
//                    myCartAdapter.notifyDataSetChanged();

                }
            }
        });








        Log.d("annu",token+"token data");
        if(flag==true){
            mycartprogress.setVisibility(View.VISIBLE);
            myCartViewModel.loadcart(token);
            order.setVisibility(View.GONE);
        }


        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyCart.this,AddAddress.class);
                startActivity(intent);
            }
        });
    }

    public void setGone(){
        recyclerView.setVisibility(View.GONE);
        mycarttot.setVisibility(View.GONE);
        total.setVisibility(View.GONE);
        order.setVisibility(View.GONE);
        empty_layout.setVisibility(View.VISIBLE);
    }


}