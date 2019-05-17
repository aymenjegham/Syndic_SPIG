package com.gst.socialcomponents.main.main;

import android.app.ProgressDialog;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.CustomFacturesMonthsAdapter;
import com.gst.socialcomponents.adapters.FacesAdapter;
import com.gst.socialcomponents.adapters.FacturesAdapter;
import com.gst.socialcomponents.adapters.YearlyReglementAdapter;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.model.Facture;
import com.gst.socialcomponents.model.Factureitemdata;
import com.gst.socialcomponents.model.HistorySyndic;
import com.gst.socialcomponents.model.InfoSyndic;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.SoldeAppartement;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
    private CustomFacturesMonthsAdapter listAdapter ;
    ArrayList<Factureitemdata> dataModels;
    private RecyclerView recyclerview ;
    private ArrayList<Integer> years;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        recyclerview=findViewById(R.id.recyclerView3);
        Toolbar toolbar = findViewById(R.id.toolbartoolactivity);
        toolbar.setTitle("Reglements");

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



        reference2 = FirebaseDatabase.getInstance().getReference().child("profiles").child(firebaseUser.getUid()).child("numresidence");
        reference2.keepSynced(true);
        reference2.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                       String appart =dataSnapshot.getValue().toString();
                        mAPIService = ApiUtils.getAPIService();
                         mAPIService.getNumChantier(residence).enqueue(new Callback<NumChantier>() {
                             @Override
                             public void onResponse(Call<NumChantier> call, Response<NumChantier> response) {
                                 Integer numchantier=Integer.valueOf(response.body().getCbmarq());
                                 mAPIService.getNumOfAppartements(appart,numchantier).enqueue(new Callback<NumAppart>() {
                                     @Override
                                     public void onResponse(Call<NumAppart> call, Response<NumAppart> response) {
                                         String numappartement =response.body().getCbmarq();
                                            mAPIService.getHistoric(numappartement).enqueue(new Callback<List<HistorySyndic>>() {
                                                @Override
                                                public void onResponse(Call<List<HistorySyndic>> call, Response<List<HistorySyndic>> response) {
                                                    Log.v("gettingnumappart",response.body().get(0).getFrais()+"    "+response.body().get(0).getYear());

                                                    years=new ArrayList<>();
                                                    for(int i=0;i<response.body().size();i++){
                                                        years.add(response.body().get(i).getYear());
                                                    }
                                                    populaterecyclerview(years,appart);
                                                }

                                                @Override
                                                public void onFailure(Call<List<HistorySyndic>> call, Throwable t) {
                                                    Log.v("gettingnumappart","fzil"+t.getMessage());

                                                }
                                            });

                                     }

                                     @Override
                                     public void onFailure(Call<NumAppart> call, Throwable t) {
                                         Toast.makeText(ToolActivity.this, "Erreur connectivité", Toast.LENGTH_SHORT).show();

                                     }
                                 });
                             }

                             @Override
                             public void onFailure(Call<NumChantier> call, Throwable t) {
                                 Toast.makeText(ToolActivity.this, "Erreur connectivité", Toast.LENGTH_SHORT).show();

                             }
                         });







                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



    }

    private void populaterecyclerview(ArrayList<Integer> years,String appart) {

        YearlyReglementAdapter adapter;
        adapter = new YearlyReglementAdapter(years,appart,ToolActivity.this,residence);
        recyclerview.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerview.setLayoutManager(layoutManager);
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

