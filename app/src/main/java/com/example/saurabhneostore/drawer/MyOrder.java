package com.example.saurabhneostore.drawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.example.saurabhneostore.adapters.MyOrderAdapter;
import com.example.saurabhneostore.model.myordermodel.Datum;
import com.example.saurabhneostore.model.myordermodel.MyOrderModel;
import com.example.saurabhneostore.viewmodel.MyOrderViewModel;
import com.example.saurabhneostore.viewmodel.MyOrderViewModelFactory;

import java.util.List;

public class MyOrder extends AppCompatActivity
{
    TextView orderid,orderdate,orderprice;
    RecyclerView recyclerView;
    MyOrderAdapter adapter;
    MyOrderViewModel myOrderViewModel;
    public static SharedPreferences sp;
    List<Datum>myorderlist;
    public static ProgressBar progressBar;
    Toolbar toolbar;
    ImageButton imageButton;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);

        orderid=findViewById(R.id.order_id);
        orderdate=findViewById(R.id.order_date);
        orderprice=findViewById(R.id.order_price);

        recyclerView=findViewById(R.id.myorder_recycler);
        progressBar=findViewById(R.id.myorder_progress_bar);
        imageButton=findViewById(R.id.myorder_backbutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyOrder.super.onBackPressed();
            }
        });

        toolbar=findViewById(R.id.myorder_tool);
        setSupportActionBar(toolbar);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        String token=sp.getString("Access","");
        Log.d("annu",token+"token check");



        myOrderViewModel=new ViewModelProvider(this,new MyOrderViewModelFactory(this)).get(MyOrderViewModel.class);
        myOrderViewModel.getMyorderlivedata().observe(this, new Observer<MyOrderModel>() {
            @Override
            public void onChanged(MyOrderModel myOrderModel) {
                myorderlist=myOrderModel.getData();
                if(myorderlist!=null)
                {
                    Log.d("annu","in onchanged ");
                    adapter=new MyOrderAdapter(MyOrder.this,myorderlist);
                    recyclerView.setAdapter(adapter);
                }

            }
        });

        progressBar.setVisibility(View.VISIBLE);
        myOrderViewModel.loadmyorder(token);
        Log.d("annu","apicall");









    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.table_menu,menu);
        MenuItem item=menu.findItem(R.id.search_menu);
        androidx.appcompat.widget.SearchView searchView=(androidx.appcompat.widget.SearchView)item.getActionView();
        //SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
