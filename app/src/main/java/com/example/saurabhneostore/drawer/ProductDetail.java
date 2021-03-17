package com.example.saurabhneostore.drawer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.adapters.ProductImageAdapter;
import com.example.saurabhneostore.model.Data;
import com.example.saurabhneostore.model.ProductImage;
import com.example.saurabhneostore.model.ProductModel;
import com.example.saurabhneostore.model.RateModel;
import com.example.saurabhneostore.viewmodel.ProductDetailViewModel;
import com.example.saurabhneostore.viewmodel.RateVM;
import com.example.saurabhneostore.viewmodel.RateVMFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetail extends AppCompatActivity {
    ImageButton backbutton, Share;
    ProductDetailViewModel productDetailViewModel;
    TextView productname, category, producer, productprice, productdesc, tooltitle;
    RatingBar ratingBar;
    public static ImageView firstimage;
    RecyclerView recyclerView;
    ProductImageAdapter adapter;
    Button buyproduct, rateproduct;
    Data list1;
    String s1;
    RateVM rateVM;
    public Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);
        backbutton = findViewById(R.id.product_backbutton);

        firstimage = findViewById(R.id.firstimage);
        buyproduct = findViewById(R.id.buynowbutton);
        rateproduct = findViewById(R.id.ratebutton);


        Share = findViewById(R.id.sharebutton);
        recyclerView = findViewById(R.id.recyclerimage);


        productname = findViewById(R.id.product_name);
        category = findViewById(R.id.product_category);
        producer = findViewById(R.id.product_producer);
        productprice = findViewById(R.id.product_price1);
        productdesc = findViewById(R.id.product_desc);
        tooltitle = findViewById(R.id.tooltitle);
        ratingBar = findViewById(R.id.product_ratingbar);

        rateproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openrate();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetail.super.onBackPressed();
            }
        });

        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Your body here";
                String shareSub = "Your subject here";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

        s1 = getIntent().getStringExtra("productid");

        Log.d("annu", "in product detail" + s1);

        rateVM = new ViewModelProvider(this, new RateVMFactory(this)).get(RateVM.class);
        rateVM.getRatingObserver().observe(this, new Observer<RateModel>() {
            @Override
            public void onChanged(RateModel rateModel) {
                if (rateModel != null) {
                    Log.d("saurabh", "Success rateing");
                }
            }
        });


        productDetailViewModel = ViewModelProviders.of(this).get(ProductDetailViewModel.class);
        productDetailViewModel.getProductModelMutableLiveData().observe(this, new Observer<ProductModel>() {
            @Override
            public void onChanged(ProductModel productModel) {
                if (productModel != null) {
                    tooltitle.setText(productModel.getData().getName());
                    productname.setText(productModel.getData().getName());
                    category.setText(productModel.getData().getProducer());
                    producer.setText(productModel.getData().getProducer());
                    productprice.setText("Rs." + productModel.getData().getCost().toString());
                    productdesc.setText(productModel.getData().getDescription());
                    ratingBar.setRating(productModel.getData().getRating().floatValue());


                    List<ProductImage> list = productModel.getData().getProductImages();
                    list1 = productModel.getData();
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),
                            LinearLayoutManager.HORIZONTAL, false);

                    recyclerView.setLayoutManager(linearLayoutManager);
                    adapter = new ProductImageAdapter(list, ProductDetail.this);
                    recyclerView.setAdapter(adapter);


                }
            }
        });

        productDetailViewModel.makeProductApiCall(s1);
    }

    private void openrate() {


        dialog = new Dialog(ProductDetail.this);
        dialog.setContentView(R.layout.product_rating);

        TextView text = dialog.findViewById(R.id.rating_title);
        text.setText(list1.getName());
        ImageView image = dialog.findViewById(R.id.rating_image);
        Picasso.with(getApplicationContext())
                .load(list1.getProductImages().get(0).getImage())
                .fit()
                .into(image);

        ratingBar =  dialog.findViewById(R.id.rating_ratingbar);
// ratingBar.setRating(ratingNumber);

        dialog.show();

        Button declineButton =  dialog.findViewById(R.id.rating_ratenowbutton);
// if decline button is clicked, close the custom dialog
        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float ratingNumber = ratingBar.getRating();

                Log.d("saurabh", "Rating " + ratingNumber);
                rateVM.loadRating(s1, String.valueOf(ratingNumber));
                dialog.dismiss();
            }
        });

    }
}
