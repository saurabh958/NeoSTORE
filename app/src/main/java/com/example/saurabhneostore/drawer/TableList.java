package com.example.saurabhneostore.drawer;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saurabhneostore.R;
import com.example.saurabhneostore.adapters.TableListAdapter;
import com.example.saurabhneostore.model.Datum;
import com.example.saurabhneostore.viewmodel.TableViewModel;

import java.util.ArrayList;
import java.util.List;

public class TableList extends AppCompatActivity {
    RecyclerView recyclerView;
    TableListAdapter tableListAdapter;
    ImageButton imageButton;
    NestedScrollView nestedScrollView;
    String category="1";
    Integer limitz=10;
    public Integer pagez=1;
    Boolean isScrolling=false;
    TableViewModel tableViewModel;
    public static ProgressBar progressBar;
    ArrayList<Datum> list1=new ArrayList<>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.tables_list);
        nestedScrollView=findViewById(R.id.nestedscroll);
        imageButton=findViewById(R.id.tablebackbutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableList.super.onBackPressed();
            }
        });

        recyclerView=findViewById(R.id.tablerecycler);
        progressBar=findViewById(R.id.progress_bar);
        Log.d("pintu","oncreate");
        LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(llm);

        tableViewModel= ViewModelProviders.of(this).get(TableViewModel.class);
        tableViewModel.getTableModelMutableLiveDataObserver().observe(this, new Observer <List<Datum>>() {
            @Override
            public void onChanged(List<Datum> data) {
                if(data!=null)
                {
                    list1.addAll(data);
                    Log.d("pintu","inonchanged");
                    tableListAdapter=new TableListAdapter(TableList.this,list1);
                    recyclerView.setAdapter(tableListAdapter);
                    tableListAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        //tableViewModel.makeApiCall(category,limitz,pagez);



//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                Log.d("pintu","onscrolled");
//                currentitem=llm.getChildCount();
//                totalitem=llm.getItemCount();
//                scrolloutitems=llm.findFirstVisibleItemPosition();
//
//                if(isScrolling || (currentitem+scrolloutitems==totalitem))
//                {
//                    isScrolling=false;
//                    pagez++;
//                    tableViewModel.makeApiCall(category,limitz,pagez);
//                    tableListAdapter.notifyDataSetChanged();
//
//                    Log.d("pintu","onscrolled");
////                    getData();
//
//                }
//            }
//
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if(newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
//                {
//                    isScrolling=true;
//                    Log.d("pintu","onscrollstatechanged");
//                }
//            }
//        });





        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY==v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight())
                {
                    pagez++;
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgressTintList(ColorStateList.valueOf(Color.RED));

                    tableViewModel.makeApiCall(category,limitz,pagez);
                    Log.d("pintu","onscrollchange executed");


                }
            }
        });

    }

//
}
