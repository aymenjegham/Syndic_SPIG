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
    private TextView balance,reste,yeartv,retenu,retenu_annuel;
    private ListView listview ;
    private CustomFacturesMonthsAdapter listAdapter ;
    ArrayList<Factureitemdata> dataModels;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool);
        balance =findViewById(R.id.bAc);
        reste =findViewById(R.id.brest);
        yeartv =findViewById(R.id.tvyear);
        retenu =findViewById(R.id.retenu);
        retenu_annuel=findViewById(R.id.retenueannuel);



        listview=findViewById(R.id.listViewmonths);



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
        final Date[] strtodate = new Date[1];

        ProgressDialog pd = new ProgressDialog(ToolActivity.this);
        pd.setMessage("chargement des données");
        pd.show();
        pd.setCancelable(false);
        mAPIService.getNumOfAppartements(appartId).enqueue(new Callback<NumAppart>() {
             @Override
            public void onResponse(Call<NumAppart> call, Response<NumAppart> response) {
                 pd.dismiss();
                 if(response.isSuccessful()) {
                     mAPIService.getInfoSyndic(Integer.valueOf(response.body().getCbmarq())).enqueue(new Callback<InfoSyndic>() {
                            int numAppart =Integer.valueOf(response.body().getCbmarq());

                        @Override
                        public void onResponse(Call<InfoSyndic> call, Response<InfoSyndic> response) {
                            date[0] =response.body().getDateRemiseCle();
                            frais[0] =response.body().getFraisupposed();
                            mAPIService.getSoldeappartement(numAppart).enqueue(new Callback<SoldeAppartement>() {
                                @Override
                                public void onResponse(Call<SoldeAppartement> call, Response<SoldeAppartement> response) {


                                    retenu_annuel.setText(String.valueOf(response.body().getsRetenu()));
                                    balance.setText(String.valueOf(response.body().getSolde()));
                                    dataModels= new ArrayList<>();
                                    dataModels.add(new Factureitemdata("Janvier", "", "",0,0));
                                    dataModels.add(new Factureitemdata("Fevrier", "", "",0,0));
                                    dataModels.add(new Factureitemdata("Mars","","",0,0));
                                    dataModels.add(new Factureitemdata("Avril", "", "",0,0));
                                    dataModels.add(new Factureitemdata("May", "", "",0,0));
                                    dataModels.add(new Factureitemdata("Juin", "", "",0,0));
                                    dataModels.add(new Factureitemdata("Juillet","","",0,0));
                                    dataModels.add(new Factureitemdata("Aout", "", "",0,0));
                                    dataModels.add(new Factureitemdata("Septembre", "", "",0,0));
                                    dataModels.add(new Factureitemdata("Octobre", "", "",0,0));
                                    dataModels.add(new Factureitemdata("Novembre","","",0,0));
                                    dataModels.add(new Factureitemdata("Decembre", "", "",0,0));
                                    listAdapter = new CustomFacturesMonthsAdapter(dataModels, getApplicationContext());
                                    listview.setAdapter( listAdapter );

                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    String dateString = format.format( new Date()   );
                                    try {
                                        strtodate[0] = format.parse ( date[0] );
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                                    Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));

                                    cal2.setTime(strtodate[0]);
                                    int year = cal2.get(Calendar.YEAR);
                                    int month = cal2.get(Calendar.MONTH);
                                    int day = cal2.get(Calendar.DAY_OF_MONTH);

                                    int yearlocal = cal.get(Calendar.YEAR);

                                    yeartv.setText(String.valueOf(yearlocal));


                                    if(year==yearlocal){
                                        int nummonthspayable=12-month;
                                        int monthamount =(response.body().getSolde()+response.body().getsRetenu())/nummonthspayable;
                                        int monthlyfrais=frais[0]/12;
                                        int monthspayable=(response.body().getSolde()+response.body().getsRetenu())/monthlyfrais;
                                        int totalpayed =nummonthspayable*monthamount;
                                        int totalmustbepayed=nummonthspayable*monthlyfrais;
                                        reste.setText(String.valueOf(totalmustbepayed-(response.body().getSolde()+response.body().getsRetenu())));
                                        retenu.setText(String.valueOf(monthspayable*monthlyfrais));


                                        dataModels.get(month).setImgview2(R.drawable.remise_key);
                                        if((month+monthspayable)<12){
                                            for(int i =month;i<month+monthspayable;i++){
                                                dataModels.get(i).setImgview(R.drawable.ic_done);
                                                dataModels.get(i).setRemise_cle(String.valueOf(monthlyfrais)+" TND");
                                            }
                                        }else{
                                            for(int i =month;i<12;i++){
                                                dataModels.get(i).setImgview(R.drawable.ic_done);
                                                dataModels.get(i).setRemise_cle(String.valueOf(monthlyfrais)+" TND");
                                            }
                                        }


                                    }else {
                                        int nummonthspayable=12;
                                        int monthamount =response.body().getSolde()/nummonthspayable;
                                        int monthlyfrais=frais[0]/12;
                                        int monthspayable=response.body().getSolde()/monthlyfrais;
                                        int totalpayed =nummonthspayable*monthamount;
                                        int totalmustbepayed=frais[0];
                                        reste.setText(String.valueOf(totalmustbepayed-response.body().getSolde()));
                                        retenu.setText(String.valueOf(monthspayable*monthlyfrais));


                                        for(int i =0;i<monthspayable;i++){
                                            dataModels.get(i).setImgview(R.drawable.ic_done);
                                            dataModels.get(i).setRemise_cle(String.valueOf(monthlyfrais)+" TND");
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(Call<SoldeAppartement> call, Throwable t) {
                                    Toast.makeText(ToolActivity.this, "Connexion au serveur échouée", Toast.LENGTH_SHORT).show();
                                    Log.v("errorcom",t.getMessage()+ "  1");

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<InfoSyndic> call, Throwable t) {
                            Toast.makeText(ToolActivity.this, "Connexion échouée", Toast.LENGTH_SHORT).show();
                            Log.v("errorcom","2"+t.getMessage());

                        }
                    });

                 }
            }
            @Override
            public void onFailure(Call<NumAppart> call, Throwable t) {
                Toast.makeText(ToolActivity.this, "Connexion au serveur échouée", Toast.LENGTH_SHORT).show();
                Log.v("errorcom",t.getMessage()+ "  3");

                pd.dismiss();



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
