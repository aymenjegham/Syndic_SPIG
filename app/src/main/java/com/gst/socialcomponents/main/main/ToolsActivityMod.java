package com.gst.socialcomponents.main.main;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
 ;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;

import com.google.gson.Gson;
import com.gst.socialcomponents.R;


import com.gst.socialcomponents.main.main.fragments.ActivationFragment;
 import com.gst.socialcomponents.main.main.fragments.InvoicingFragment;
import com.gst.socialcomponents.main.main.fragments.ReunionFragment;
import com.gst.socialcomponents.model.Profilefire;


import java.util.ArrayList;

public class ToolsActivityMod extends AppCompatActivity implements ActivationFragment.OnFragmentInteractionListener, InvoicingFragment.OnFragmentInteractionListener,ReunionFragment.OnFragmentInteractionListener {


    Toolbar toolbar;
    public ActionBar actionBar;
    private DatabaseReference reference1,reference2;
    private ConstraintLayout constraintLayout;
    String residence ;
     TabLayout tablayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_mod);

        tablayout=findViewById(R.id.tab_layouttoolsmod);
        toolbar = findViewById(R.id.toolbartoolactivitymod);
        toolbar.setTitle("Moderateur");
        toolbar.setBackgroundColor(0xffB22222);
        tablayout.setBackgroundColor(0xffB22222);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        SharedPreferences prefs = getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final TabLayout tabLayout =findViewById(R.id.tab_layouttoolsmod);
        final ViewPager viewPager =findViewById(R.id.view_pagertoolsmod);
        ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new ActivationFragment(),"Activation");
        viewPagerAdapter.addFragment(new InvoicingFragment(),"Facturation");
        viewPagerAdapter.addFragment(new ReunionFragment(),"Réunions");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;


        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
