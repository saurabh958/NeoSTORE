package com.example.saurabhneostore.homepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.saurabhneostore.Login.Login;
import com.example.saurabhneostore.R;
import com.example.saurabhneostore.adapters.ViewPagerAdapter;
import com.example.saurabhneostore.drawer.MyAccount;
import com.example.saurabhneostore.drawer.MyCart;
import com.example.saurabhneostore.drawer.TableList;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CircleImageView circleImageView;
    TextView tv1,tv2;
    ViewPager mviewpager;
    TabLayout tabLayout;


    int[] images={R.drawable.sliderimage1,R.drawable.sliderimage2,R.drawable.imageslider3,R.drawable.sliderimage4};

    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        drawerLayout=findViewById(R.id.drawerly);
        navigationView=findViewById(R.id.navview);
        toolbar=findViewById(R.id.toolbar);

        View headerContainer = navigationView.getHeaderView(0);
        tv1=headerContainer.findViewById(R.id.currentname);
        tv2=headerContainer.findViewById(R.id.currentemail);
        circleImageView=headerContainer.findViewById(R.id.image1);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.navigationdrawer_open,R.string.navigationdrawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

//        MyData myData=Appconstant.mydatas.get(0);
//        String s1=myData.fnames+" "+myData.lnames;
//        String s2=myData.emailids;
//        tv1.setText(s1);
//        tv2.setText(s2);
        SharedPreferences sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
        tv1.setText(sp.getString("FName","")+" "+sp.getString("LName",""));
        tv2.setText(sp.getString("Email",""));

        String imagehome=sp.getString("Pic","");
        Log.d("pintu","prefs - string image"+imagehome);
        Picasso.with(getApplicationContext())
                .load(imagehome)
                .fit()
                .into(circleImageView);


        mviewpager= findViewById(R.id.viewpagerm);
        viewPagerAdapter= new ViewPagerAdapter(Homepage.this,images);
        mviewpager.setAdapter(viewPagerAdapter);

        tabLayout=findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mviewpager,true);



        Timer timer=new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),2000,4000);


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();
        if(id==R.id.mycart)
        {
            Intent intent=new Intent(Homepage.this, MyCart.class);
            startActivity(intent);
        }
        else if(id==R.id.myaccount)
        {
            Intent intent=new Intent(Homepage.this, MyAccount.class);
            startActivity(intent);
        }
        else if (id==R.id.tables)
        {
            Intent intent=new Intent(Homepage.this, TableList.class);
            startActivity(intent);
        }
        else if(id==R.id.logout)
        {
            SharedPreferences sp = getSharedPreferences(Login.PREFS_NAME,MODE_PRIVATE);
            SharedPreferences.Editor myedit=sp.edit();
            myedit.putString("FName","");
            myedit.clear();
            myedit.commit();
//            sp.getBoolean("hasLoggedIn",false);
            Intent intent=new Intent(Homepage.this, Login.class);
            startActivity(intent);
            finish();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            Homepage.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mviewpager.getCurrentItem() < images.length - 1) {
                        mviewpager.setCurrentItem(mviewpager.getCurrentItem() + 1);
                    } else {
                        mviewpager.setCurrentItem(0);
                    }
                }
            });
        }
    }


}
