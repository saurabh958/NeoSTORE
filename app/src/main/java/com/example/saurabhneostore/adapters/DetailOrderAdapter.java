package com.example.saurabhneostore.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.model.myordermodel.OrderDetail;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.DetailViewHolder>
{

    private Context context;
    private List<OrderDetail>orderDetails;

    public DetailOrderAdapter(Context context, List<OrderDetail> orderDetails) {
        this.context = context;
        this.orderDetails = orderDetails;
    }

    @NonNull
    @Override
    public DetailOrderAdapter.DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.detail_row,parent,false);
        return new DetailOrderAdapter.DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailOrderAdapter.DetailViewHolder holder, int position) {
        holder.name.setText(orderDetails.get(position).getProdName());
        holder.category.setText(orderDetails.get(position).getProdCatName());
        holder.price.setText("₹"+orderDetails.get(position).getTotal());
        holder.quanti.setText("QTY : "+orderDetails.get(position).getQuantity());
        Picasso.with(context)
                .load(orderDetails.get(position).getProdImage())
                .fit()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(this.orderDetails!=null)
        {
            return this.orderDetails.size();
        }
        return 0;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView name,price,category,quanti;


        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.detail_image);
            name=itemView.findViewById(R.id.detail_name);
            price=itemView.findViewById(R.id.detail_cost);
            category=itemView.findViewById(R.id.detail_category);
            quanti=itemView.findViewById(R.id.detail_qty);
        }
    }
}
