package com.example.saurabhneostore.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.drawer.DetailOrder;
import com.example.saurabhneostore.model.myordermodel.Datum;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderviewholder> {

    private Context context;
    private List<Datum>orderlist;

    public MyOrderAdapter(Context context, List<Datum> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }

    @NonNull
    @Override
    public MyOrderAdapter.MyOrderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.myorder_row,parent,false);
        return new MyOrderAdapter.MyOrderviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.MyOrderviewholder holder, int position) {

        holder.orderid.setText(orderlist.get(position).getId().toString());
        holder.orderprice.setText("₹"+orderlist.get(position).getCost().toString());
        holder.orderdate.setText(orderlist.get(position).getCreated());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailOrder.class);
                String s1= String.valueOf(orderlist.get(position).getId());
                Log.d("annu",s1);
                intent.putExtra("productid",s1);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        if(this.orderlist!=null)
        {
            return this.orderlist.size();
        }
        return 0;
    }

    public class MyOrderviewholder extends RecyclerView.ViewHolder
    {
        TextView orderid,orderdate,orderprice;

        public MyOrderviewholder(@NonNull View itemView) {
            super(itemView);
            orderdate=itemView.findViewById(R.id.order_date);
            orderid=itemView.findViewById(R.id.order_id);
            orderprice=itemView.findViewById(R.id.order_price);
        }
    }
}
