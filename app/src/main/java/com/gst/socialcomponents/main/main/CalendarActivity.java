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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.ReunionAdapter;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.model.DataReunion;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.NumReunion;
import com.gst.socialcomponents.model.NumUser;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarActivity extends AppCompatActivity {

    public ActionBar actionBar;
    String residence;
    String email;
    String numresidence;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    MaterialCalendarView calendarView;
    private APIService mAPIService;
    Integer numuserprofile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);



        calendarView = findViewById(R.id.calendar);
        calendarView.setSelectionMode (MaterialCalendarView.SELECTION_MODE_MULTIPLE);
        calendarView.setSelectionMode (MaterialCalendarView.SELECTION_MODE_NONE);



        Toolbar toolbar = findViewById(R.id.toolbarcalendaractivity);
        toolbar.setTitle("Mes réunions");

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs = getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);
        email=prefs.getString("sharedprefemail",null);
        numresidence=prefs.getString("sharedprefnumresidence",null);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }

        ArrayList<Response<DataReunion>> reunions = new ArrayList() ;
        ArrayList<Integer> intuser = new ArrayList() ;



        mAPIService = ApiUtils.getAPIService();
        mAPIService.getNumChantier(residence).enqueue(new Callback<NumChantier>() {
            @Override
            public void onResponse(Call<NumChantier> call, Response<NumChantier> response) {
            Integer numchantier=Integer.valueOf(response.body().getCbmarq());

                mAPIService.getNumOfAppartements(numresidence,numchantier).enqueue(new Callback<NumAppart>() {
                    @Override
                    public void onResponse(Call<NumAppart> call, Response<NumAppart> response) {
                        String iDUser=response.body().getCbmarq();


                        mAPIService.getIdReunion(Integer.valueOf(iDUser)).enqueue(new Callback<List<NumReunion>>() {
                            @Override
                            public void onResponse(Call<List<NumReunion>> call, Response<List<NumReunion>> response) {
                                reunions.clear();
                                intuser.clear();
                                Integer  sizeReunion= response.body().size();
                                List<NumReunion> response1=response.body();

                                mAPIService.getIdUser(email).enqueue(new Callback<NumUser>() {
                                    @Override
                                    public void onResponse(Call<NumUser> call, Response<NumUser> response) {
                                        numuserprofile=response.body().getCbmarq();
                                        for (int i = 0; i < sizeReunion; i++) {
                                            mAPIService.getReunion(response1.get(i).getReunionid()).enqueue(new Callback<DataReunion>() {
                                                @Override
                                                public void onResponse(Call<DataReunion> call, Response<DataReunion> response) {
                                                    reunions.add(response);
                                                    intuser.add(Integer.valueOf(numuserprofile));
                                                    setupRecyclerview(reunions,intuser);
                                                    try {
                                                        setupCalendar(reunions);
                                                    } catch (ParseException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                                @Override
                                                public void onFailure(Call<DataReunion> call, Throwable t) {
                                                    Log.v("testingresult",t.getMessage()+"   1");
                                                    Toast.makeText(CalendarActivity.this, "Probleme connection", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<NumUser> call, Throwable t) {
                                        Log.v("testingresult",t.getMessage()+  "  2");

                                    }

                                });
                            }
                            @Override
                            public void onFailure(Call<List<NumReunion>> call, Throwable t) {
                                Toast.makeText(CalendarActivity.this, "Pas de reunions pour vous !", Toast.LENGTH_LONG).show();
                                Log.v("testingresult",t.getMessage()+"   3");


                            }
                        });
                    }
                    @Override
                    public void onFailure(Call<NumAppart> call, Throwable t) {
                        Toast.makeText(CalendarActivity.this, "Probleme connection", Toast.LENGTH_SHORT).show();
                        Log.v("testingresult",t.getMessage()+"   4");


                    }
                });
            }

            @Override
            public void onFailure(Call<NumChantier> call, Throwable t) {

            }
        });

    }


/*
        ArrayList<ReunionRetrieve> reunions = new ArrayList() ;

        reference = FirebaseDatabase.getInstance().getReference().child("Reunions").child(residence);
        reference.keepSynced(true);
        reference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        reunions.clear();
                         for(DataSnapshot ds : dataSnapshot.getChildren()) {
                           ReunionRetrieve reunion = ds.getValue(ReunionRetrieve.class);
                             ArrayList<Coming> members=new ArrayList<>();
                             members=reunion.getUserids();

                             for(int i=0;i<members.size();i++){

                                 if(members.get(i).getUsersid() .equals(firebaseUser.getUid())){
                                     reunions.add(reunion);
                             }
                         }
                        }
                        try {
                            setupCalendar(reunions);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(CalendarActivity.this, "Connection error", Toast.LENGTH_SHORT).show();
                    }
                });

        */



    private void setupCalendar(ArrayList<Response<DataReunion>> reunions) throws ParseException {

        ArrayList<Integer> Day=new ArrayList<Integer>();
        ArrayList<Integer> Month=new ArrayList<Integer>();
        ArrayList<Integer> Year=new ArrayList<Integer>();


        for(int i=0;i<reunions.size();i++) {

            String selectedDate = reunions.get(i).body().getR_datedebut();



            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date datedebu   = format.parse ( selectedDate );
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                cal.setTime(datedebu);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                 Day.add(day);
                Month.add(month);
                Year.add(year);


            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        for(int i=0 ;i< Day.size();i++){
            String year =Year.get(i).toString();
             int intye =Integer.valueOf(year);
            calendarView.setDateSelected(CalendarDay.from(intye,Month.get(i)+1,Day.get(i)),true);
         }

    }
/*
    private void setupRecyclerview(ArrayList<ReunionRetrieve> reunions) {
        RecyclerView recyclerView =findViewById(R.id.recyclerviewreunions);
        recyclerView.setHasFixedSize(false);
         ReunionsAdapter adapter;
        adapter=new ReunionsAdapter(reunions,getApplicationContext());
        Collections.reverse(reunions);
        recyclerView.setAdapter(adapter);
          LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
         layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }
*/

    private void setupRecyclerview(ArrayList<Response<DataReunion>> reunions, ArrayList<Integer> intuser) {
        RecyclerView recyclerView =findViewById(R.id.recyclerviewreunions);
        recyclerView.setHasFixedSize(false);

        ReunionAdapter adapter;
        adapter=new ReunionAdapter(reunions,getApplicationContext(),intuser,getApplicationContext());

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



}
