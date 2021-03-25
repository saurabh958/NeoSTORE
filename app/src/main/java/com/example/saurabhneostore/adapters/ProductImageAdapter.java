package com.example.saurabhneostore.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.drawer.ProductDetail;
import com.example.saurabhneostore.model.ProductImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ProductViewHolder> {

    private List<ProductImage>productImages;
    private Context context;
    int selectedItem;

    public ProductImageAdapter(List<ProductImage> productImages, Context context) {
        this.productImages = productImages;
        this.context = context;
    }



    @NonNull
    @Override
    public ProductImageAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_images,parent,false);
        ProductViewHolder viewHolder=new ProductViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductImageAdapter.ProductViewHolder holder, int position)
    {
        Picasso.with(context)
                .load(productImages.get(position).getImage())
                .fit()
                .into(holder.imageView);

        Picasso.with(context)
                .load(productImages.get(0).getImage())
                .fit()
                .into(ProductDetail.firstimage);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItem=position;

                notifyDataSetChanged();

            }
        });

        if(selectedItem==position)
        {
            Drawable d=context.getResources().getDrawable(R.drawable.stroke_red);
            holder.itemView.setBackground(d);

        }
        else{
            Drawable d=context.getResources().getDrawable(R.drawable.stroke_black);
            holder.itemView.setBackground(d);

        }
        Picasso.with(context)
                .load(productImages.get(selectedItem).getImage())
                .fit()
                .into(ProductDetail.firstimage);


    }



    @Override
    public int getItemCount() {
        return productImages.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.product_photo);
        }
    }
}
