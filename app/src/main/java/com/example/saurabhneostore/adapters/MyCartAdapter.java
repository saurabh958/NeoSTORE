package com.example.saurabhneostore.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.example.saurabhneostore.drawer.MyCart;
import com.example.saurabhneostore.model.MyCart.Datum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartViewHolder> {

    private Context context;
    private List<Datum>cartlist;
    public SharedPreferences sp;




    public MyCartAdapter(Context context, List<Datum> cartlist) {
        this.context = context;
        this.cartlist = cartlist;
    }



    @NonNull
    @Override
    public MyCartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.mycart_row,parent,false);
        return new MyCartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCartAdapter.CartViewHolder holder, int position) {
        Log.d("annu","in onbindviewholder setting recycler data");

        holder.name.setText(cartlist.get(position).getProduct().getName());
            holder.category.setText(cartlist.get(position).getProduct().getProductCategory());
            holder.cost.setText("₹"+cartlist.get(position).getProduct().getSubTotal());
            Picasso.with(context)
                    .load(cartlist.get(position).getProduct().getProductImages())
                    .fit()
                    .into(holder.cartimage);


        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");

        ArrayAdapter<String> dataAdapter =new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item,list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantity.setAdapter(dataAdapter);
        int spinnerPosition = cartlist.get(position).getQuantity();
        Log.d("annu",cartlist.get(position).getQuantity().toString());
        holder.quantity.setSelection(spinnerPosition-1);
        Log.d("annu", String.valueOf(spinnerPosition));


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("annu","in deletecart onclick ");
                sp = context.getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
                String token=sp.getString("Access","");
                Log.d("annu","token"+token);
                String id=cartlist.get(position).getProductId().toString();
                Log.d("annu","productid is"+id);
                MyCart.deleteCartItemViewModel.deleteCartItem(token,id);
                Log.d("annu","deletecartcalled");
            }
        });
    }

    @Override
    public int getItemCount() {
        if(this.cartlist!=null)
        {
            return this.cartlist.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView cartimage;
        TextView name,category,cost;
        Spinner quantity;
        ImageButton delete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            cartimage=itemView.findViewById(R.id.mycart_image);
            name=itemView.findViewById(R.id.mycart_name);
            category=itemView.findViewById(R.id.mycart_category);
            cost=itemView.findViewById(R.id.mycart_cost);
            quantity=itemView.findViewById(R.id.mycart_spinner);
            delete=itemView.findViewById(R.id.mycart_delete);



            quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item=parent.getItemAtPosition(position).toString();
                    Log.d("annu",item);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Log.d("annu","onnothing selected");

                }
            });
        }
    }
}
