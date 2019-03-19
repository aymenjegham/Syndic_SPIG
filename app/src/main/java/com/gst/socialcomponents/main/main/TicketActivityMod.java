package com.gst.socialcomponents.main.main;

import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.main.fragments.ClosedFragment;
import com.gst.socialcomponents.main.main.fragments.ProcessessFragment;
import com.gst.socialcomponents.main.main.fragments.UnreadFragment;

import java.util.ArrayList;

public class TicketActivityMod extends AppCompatActivity implements UnreadFragment.OnFragmentInteractionListener, ProcessessFragment.OnFragmentInteractionListener ,ClosedFragment.OnFragmentInteractionListener{



    Toolbar toolbar;
    TabLayout tablayout;
    public ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_mod);


        toolbar = findViewById(R.id.toolbarticketmod);
        tablayout=findViewById(R.id.tab_layout);
        toolbar.setTitle("Moderateur");
        toolbar.setBackgroundColor(0xffB22222);
        tablayout.setBackgroundColor(0xffB22222);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        final TabLayout tabLayout =findViewById(R.id.tab_layout);
        final ViewPager viewPager =findViewById(R.id.view_pager);
        ViewPagerAdapter viewPagerAdapter =new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new UnreadFragment(),"Nouveaux");
        viewPagerAdapter.addFragment(new ProcessessFragment(),"En cours");
        viewPagerAdapter.addFragment(new ClosedFragment(),"Cloturé");


        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);




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
}
