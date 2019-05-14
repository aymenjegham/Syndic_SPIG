package com.gst.socialcomponents.main.main;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.UserAdapterdl;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.model.NumPrincipal;
import com.gst.socialcomponents.model.Profilefire;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SettingsActivity extends AppCompatActivity {

    public ActionBar actionBar;
    RecyclerView recyclerView;
    String residence,numresidence,email;
    private DatabaseReference reference1,reference2;
    FirebaseUser firebaseUser;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        recyclerView = findViewById(R.id.recycler_view_parain);


        Toolbar toolbar = findViewById(R.id.toolbarconfig);
        toolbar.setTitle("Configuration");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();


        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs =getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);
        numresidence=prefs.getString( "sharedprefnumresidence",null);
        email=prefs.getString( "sharedprefemail",null);


        mAPIService = ApiUtils.getAPIService();
        mAPIService.getPrincipal(email).enqueue(new Callback<NumPrincipal>() {
            @Override
            public void onResponse(Call<NumPrincipal> call, Response<NumPrincipal> response) {

                 if(response.body().getCbmarq() == 1){
                    getdata();
                }
            }

            @Override
            public void onFailure(Call<NumPrincipal> call, Throwable t) {

            }
        });



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
                            if(profile.getNumresidence().equals(numresidence)){
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

    void retrivedata(ArrayList profiles){

        recyclerView.setHasFixedSize(true);
        UserAdapterdl adapter;
        adapter=new UserAdapterdl(profiles);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
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
}
