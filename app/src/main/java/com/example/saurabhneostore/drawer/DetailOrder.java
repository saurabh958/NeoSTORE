package com.example.saurabhneostore.drawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.example.saurabhneostore.adapters.DetailOrderAdapter;
import com.example.saurabhneostore.model.myordermodel.OrderDetail;
import com.example.saurabhneostore.viewmodel.DetailOrderViewModel;
import com.example.saurabhneostore.viewmodel.DetailOrderViewModelFactory;

import java.util.List;

public class DetailOrder extends AppCompatActivity
{
    RecyclerView recyclerView;
    DetailOrderViewModel detailOrderViewModel;
    DetailOrderAdapter detailOrderAdapter;
    TextView cost,detailtitle;
    public static SharedPreferences sp;
    String s1;
    List<OrderDetail>orderDetailList;
    public static ProgressBar progressBar;
    ImageButton backbutton;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);

        cost=findViewById(R.id.detail_price);
        recyclerView=findViewById(R.id.detail_recycler);
        detailtitle=findViewById(R.id.detail_title);
        progressBar=findViewById(R.id.orderdetail_progress_bar);
        backbutton=findViewById(R.id.detail_backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailOrder.super.onBackPressed();
            }
        });



        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);

        sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        String token=sp.getString("Access","");
        Log.d("annu",token+"token check");

        s1 = getIntent().getStringExtra("productid");
        detailtitle.setText("Order ID : "+s1);

        detailOrderViewModel=new ViewModelProvider(this,new DetailOrderViewModelFactory(this)).get(DetailOrderViewModel.class);
        detailOrderViewModel.getDetailOrderMutableLiveData().observe(this, new Observer<com.example.saurabhneostore.model.myordermodel.DetailOrder>() {
            @Override
            public void onChanged(com.example.saurabhneostore.model.myordermodel.DetailOrder detailOrder) {
                orderDetailList=detailOrder.getData().getOrderDetails();
                if(orderDetailList!=null)
                {

                    detailOrderAdapter=new DetailOrderAdapter(DetailOrder.this,orderDetailList);
                    recyclerView.setAdapter(detailOrderAdapter);
                    cost.setText("₹"+detailOrder.getData().getCost());

                }
            }

        });

progressBar.setVisibility(View.VISIBLE);
        detailOrderViewModel.loaddetailorder(token,s1);





    }
}
