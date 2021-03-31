package com.example.saurabhneostore.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.drawer.ProductDetail;
import com.example.saurabhneostore.model.Datum;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.TableViewHolder> implements Filterable
{
    private Context context;
    private List<Datum>itemsList;
    private List<Datum>backup;



    public TableListAdapter(Context context, List<Datum> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
        backup=new ArrayList<>(itemsList);

    }



    public void setItemsList(List<Datum> itemsList)
    {

         this.itemsList=itemsList;
         notifyDataSetChanged();
    }



    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.table_row,parent,false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        holder.name.setText(itemsList.get(position).getName());
        holder.desc.setText(itemsList.get(position).getProducer());
        holder.price.setText("RS."+itemsList.get(position).getCost());
        holder.ratingBar.setRating(itemsList.get(position).getRating().floatValue());
        Picasso.with(context)
                .load(itemsList.get(position).getProductImages())
                .fit()
                .into(holder.tableimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, ProductDetail.class);
                String s1= String.valueOf(itemsList.get(position).getId());
                String s2=itemsList.get(position).getProductImages();
                Log.d("annu",s1);
                intent.putExtra("productid",s1);

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        if(this.itemsList!=null)
        {
            return this.itemsList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword)
        {
            ArrayList<Datum>filterdata=new ArrayList<>();
            if(keyword.toString().isEmpty())
            {
                filterdata.addAll(backup);
            }
            else
            {
                for(Datum obj:backup)
                {
                    if(obj.getName().toString().toLowerCase().contains(keyword.toString().toLowerCase()))
                    {
                        filterdata.add(obj);

                    }
                }
            }

            FilterResults results=new FilterResults();
            results.values=filterdata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            itemsList.clear();
            itemsList.addAll((ArrayList<Datum>)results.values);
            notifyDataSetChanged();


        }
    };

    public class TableViewHolder extends RecyclerView.ViewHolder
    {
        ImageView tableimage;
        TextView name,desc,price;
        RatingBar ratingBar;

        public TableViewHolder(View itemView)
        {
            super(itemView);
            tableimage=itemView.findViewById(R.id.tableimage);
            name=itemView.findViewById(R.id.nametextview);
            desc=itemView.findViewById(R.id.desctextview);
            price=itemView.findViewById(R.id.pricetxtview);
            ratingBar=itemView.findViewById(R.id.ratingbar);
        }
    }

}
