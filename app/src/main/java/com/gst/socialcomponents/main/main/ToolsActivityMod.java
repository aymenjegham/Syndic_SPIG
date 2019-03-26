package com.gst.socialcomponents.main.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.SearchUsersAdapter;
import com.gst.socialcomponents.adapters.TicketAdapter;
import com.gst.socialcomponents.adapters.UserAdapterdl;
import com.gst.socialcomponents.adapters.UsersAdapter;
import com.gst.socialcomponents.adapters.holders.UserViewHolder;
import com.gst.socialcomponents.main.base.BasePresenter;
import com.gst.socialcomponents.main.profile.ProfileActivity;
import com.gst.socialcomponents.main.search.users.SearchUsersFragment;
import com.gst.socialcomponents.main.search.users.SearchUsersPresenter;
import com.gst.socialcomponents.main.search.users.SearchUsersView;
import com.gst.socialcomponents.managers.ProfileManager;
import com.gst.socialcomponents.model.Profile;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.LogUtil;
import com.gst.socialcomponents.views.FollowButton;

import java.util.ArrayList;

public class ToolsActivityMod extends AppCompatActivity {


    Toolbar toolbar;
    public ActionBar actionBar;
    RecyclerView recyclerView;
    private DatabaseReference reference1,reference2;
    private ConstraintLayout constraintLayout;
    String residence ;
    FirebaseUser firebaseUser;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_mod);


        toolbar = findViewById(R.id.toolbartoolactivitymod);
        toolbar.setTitle("Moderateur");
        toolbar.setBackgroundColor(0xffB22222);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        constraintLayout=findViewById(R.id.activitytoolsmod);

        SharedPreferences prefs = getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
            getdata();
    }

    private void getdata() {

        ArrayList<Profilefire> profiles = new ArrayList() ;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        reference1 = FirebaseDatabase.getInstance().getReference().child("profiles");
        reference1.keepSynced(true);
        reference1.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        profiles.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                               Profilefire profile = ds.getValue(Profilefire.class);
                                if(profile.getResidence().equals(residence)){
                                    if(!profile.getId().equals(firebaseUser.getUid())){
                                        profiles.add(profile);

                                    }
                                }
                        }
                        retrivedata(profiles);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "error occured", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void changestate(String userid,Boolean state,View view){


            changeactivationstate(userid,state);



        String message = "";
        if(state){
            message="Compte activé";
         }else {
            message="Compte Désactivé";
        }
        int duration = Snackbar.LENGTH_SHORT;
        showSnackbar(view, message, duration);
    }

    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
    }

    void retrivedata(ArrayList profiles){

        recyclerView = findViewById(R.id.recycler_view_toolmod);
        recyclerView.setHasFixedSize(true);
        UserAdapterdl adapter;
        adapter=new UserAdapterdl(profiles);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
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




    void changeactivationstate(String userid,Boolean state){

        reference2 = FirebaseDatabase.getInstance().getReference().child("profiles");
        reference2.keepSynced(true);
        reference2.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {

                                Profilefire profilefire = ds.getValue(Profilefire.class);
                                if((profilefire.getId().equals(userid))){
                                    reference2.child(userid).child("active").setValue(state);
                             }

                        }
                     }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error connexion", Toast.LENGTH_SHORT).show();
                    }
                });


    }





}
