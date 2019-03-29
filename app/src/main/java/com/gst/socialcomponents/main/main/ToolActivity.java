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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.FacesAdapter;
import com.gst.socialcomponents.adapters.FacturesAdapter;
import com.gst.socialcomponents.model.Facture;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ToolActivity extends AppCompatActivity {


    public ActionBar actionBar;
    String residence;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);


        Toolbar toolbar = findViewById(R.id.toolbartoolactivity);
        toolbar.setTitle("Mes factures");

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs = getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }

        ArrayList<Facture> factures = new ArrayList() ;

        reference = FirebaseDatabase.getInstance().getReference().child("Frais").child(residence).child(firebaseUser.getUid());
        reference.keepSynced(true);
        reference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        factures.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                            Map<String, TicketRetrieve> td = (HashMap<String, TicketRetrieve>) ds.getValue();
                            Facture facture = ds.getValue(Facture.class);
                           factures.add(facture);
                            retrivedata(factures);


                        }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });











    }


    void retrivedata(ArrayList factures) {
        RecyclerView recyclerView = findViewById(R.id.rvfactures);
        recyclerView.setHasFixedSize(true);
        FacturesAdapter adapter;
        adapter = new FacturesAdapter(factures);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
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
