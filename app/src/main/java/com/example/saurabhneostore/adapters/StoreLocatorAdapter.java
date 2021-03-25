package com.example.saurabhneostore.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.model.Store.LocationModel;

import java.util.List;


public class StoreLocatorAdapter extends RecyclerView.Adapter<StoreLocatorAdapter.MyViewHolder> {


    List<LocationModel> al;
    Context context;

    public StoreLocatorAdapter(Context context, List<LocationModel> al) {
        this.context = context;
        this.al = al;
    }

    @Override
    public StoreLocatorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_row, parent, false);
        StoreLocatorAdapter.MyViewHolder vh = new StoreLocatorAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.store.setText(al.get(position).getStoreName());
        holder.address.setText(al.get(position).getStoreAddress());
        Log.d("saurabh ","name "+al.get(position).getStoreName());
    }

    @Override
    public int getItemCount() {
        System.out.println("-=-----------" + al.size());
        System.out.println(al.size());
        return al.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView store,address;

        public MyViewHolder(View itemView) {
            super(itemView);

            store = itemView.findViewById(R.id.store_name);
            address =itemView.findViewById(R.id.store_address);
        }

    }
}
