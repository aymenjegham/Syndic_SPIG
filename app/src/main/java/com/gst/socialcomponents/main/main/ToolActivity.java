package com.gst.socialcomponents.main.main;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.gst.socialcomponents.adapters.CustomFacturesMonthsAdapter;
import com.gst.socialcomponents.adapters.FacesAdapter;
import com.gst.socialcomponents.adapters.FacturesAdapter;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.model.Facture;
import com.gst.socialcomponents.model.Factureitemdata;
import com.gst.socialcomponents.model.InfoSyndic;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.SoldeAppartement;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Headers;

public class ToolActivity extends AppCompatActivity {


    public ActionBar actionBar;
    String residence;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference,reference2;
    private APIService mAPIService;
    private TextView balance;
    private ListView listview ;
    private CustomFacturesMonthsAdapter listAdapter ;
    ArrayList<Factureitemdata> dataModels;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        balance =findViewById(R.id.bAc);
        listview=findViewById(R.id.listViewmonths);



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





     /*   reference = FirebaseDatabase.getInstance().getReference().child("Frais").child(residence).child(firebaseUser.getUid());
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


*/

        reference2 = FirebaseDatabase.getInstance().getReference().child("profiles").child(firebaseUser.getUid()).child("numresidence");
        reference2.keepSynced(true);
        reference2.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       String appart =dataSnapshot.getValue().toString();
                        mAPIService = ApiUtils.getAPIService();
                        getcbmarq(appart);

                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



    }
    public void getcbmarq(String appartId) {
        final String[] date = new String[1];
        final int[] frais = new int[1];
        mAPIService.getNumOfAppartements(appartId).enqueue(new Callback<NumAppart>() {

             @Override
            public void onResponse(Call<NumAppart> call, Response<NumAppart> response) {
                if(response.isSuccessful()) {
                     mAPIService.getInfoSyndic(Integer.valueOf(response.body().getCbmarq())).enqueue(new Callback<InfoSyndic>() {
                        @Override
                        public void onResponse(Call<InfoSyndic> call, Response<InfoSyndic> response) {
                            date[0] =response.body().getDateRemiseCle();
                            frais[0] =response.body().getFraisupposed();

                        }

                        @Override
                        public void onFailure(Call<InfoSyndic> call, Throwable t) {
                            Toast.makeText(ToolActivity.this, "Connexion échouée", Toast.LENGTH_SHORT).show();
                        }
                    });
                    mAPIService.getSoldeappartement(Integer.valueOf(response.body().getCbmarq())).enqueue(new Callback<SoldeAppartement>() {
                        @Override
                        public void onResponse(Call<SoldeAppartement> call, Response<SoldeAppartement> response) {
                            Log.v("gettingvaluefromwe",response.body().getSolde()+" "+date[0]+"   "+frais[0]);
                            balance.setText(String.valueOf(response.body().getSolde()));
                            dataModels= new ArrayList<>();
                            dataModels.add(new Factureitemdata("Janvier", "", "",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Fevrier", "", "",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Mars","","",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Avril", "", "",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("May", "", "",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Juin", "", "",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Juillet","","",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Aout", "", "",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Septembre", "", "",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Octobre", "", "",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Novembre","","",R.drawable.ic_done));
                            dataModels.add(new Factureitemdata("Decembre", "", "",R.drawable.ic_done));
                            listAdapter = new CustomFacturesMonthsAdapter(dataModels, getApplicationContext());
                            listview.setAdapter( listAdapter );


                        }

                        @Override
                        public void onFailure(Call<SoldeAppartement> call, Throwable t) {
                            Toast.makeText(ToolActivity.this, "Connexion échouée", Toast.LENGTH_SHORT).show();

                        }
                    });
                 }
            }
            @Override
            public void onFailure(Call<NumAppart> call, Throwable t) {
                Toast.makeText(ToolActivity.this, "Connexion au serveur échouée", Toast.LENGTH_SHORT).show();

            }
        });
    }

/*
    void retrivedata(ArrayList factures) {
        RecyclerView recyclerView = findViewById(R.id.rvfactures);
        recyclerView.setHasFixedSize(true);
        FacturesAdapter adapter;
        Collections.reverse(factures);
        adapter = new FacturesAdapter(factures);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    */

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
