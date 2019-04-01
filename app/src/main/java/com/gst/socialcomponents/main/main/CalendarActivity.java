package com.gst.socialcomponents.main.main;

import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import com.gst.socialcomponents.adapters.ReunionsAdapter;
import com.gst.socialcomponents.adapters.TicketAdapter;
import com.gst.socialcomponents.model.Coming;
import com.gst.socialcomponents.model.ReunionRetrieve;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.vo.DateData;


public class CalendarActivity extends AppCompatActivity {

    public ActionBar actionBar;
    String residence;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    MaterialCalendarView calendarView;



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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }


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

    }

    private void setupCalendar(ArrayList<ReunionRetrieve> reunions) throws ParseException {

        ArrayList<Integer> Day=new ArrayList<Integer>();
        ArrayList<Integer> Month=new ArrayList<Integer>();
        ArrayList<Integer> Year=new ArrayList<Integer>();

        for(int i=0;i<reunions.size();i++) {

            String selectedDate = reunions.get(i).getDate();

            String parts[] = selectedDate.split("/");

            int day = Integer.parseInt(parts[0]);
            Day.add(day);
            int month = Integer.parseInt(parts[1]);
            Month.add(month);
            int year = Integer.parseInt(parts[2]);
            Year.add(year);

        }

        for(int i=0 ;i< Day.size();i++){

            String year =Year.get(i).toString();
            String yeari=String.valueOf("20"+year);
            int intye =Integer.valueOf(yeari);
            calendarView.setDateSelected(CalendarDay.from(intye,Month.get(i),Day.get(i)),true);
            setupRecyclerview(reunions);
        }

    }

    private void setupRecyclerview(ArrayList<ReunionRetrieve> reunions) {

        RecyclerView recyclerView =findViewById(R.id.recyclerviewreunions);
        recyclerView.setHasFixedSize(false);
         ReunionsAdapter adapter;
        adapter=new ReunionsAdapter(reunions);
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
