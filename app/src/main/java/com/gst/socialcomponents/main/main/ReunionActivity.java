package com.gst.socialcomponents.main.main;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.FacesAdapter;
import com.gst.socialcomponents.model.Coming;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.Reunion;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ReunionActivity extends AppCompatActivity {


    Toolbar toolbar;
    public ActionBar actionBar;
    FirebaseUser firebaseUser;
    DatabaseReference reference,reference1;
    EditText titrer,sujetr,datet,heuret,emplacer;
    TextView dater,heurer;
    Button confirmr;
    final Calendar myCalendar1= Calendar.getInstance();
    String residence;
    ArrayList<Coming> userids=new ArrayList<>();
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reunion);
        RecyclerView recyclerView = findViewById(R.id.rvfacesreunion);
        toolbar = findViewById(R.id.toolbarreunion);
        toolbar.setTitle("Moderateur");
        toolbar.setBackgroundColor(0xffB22222);
        titrer=findViewById(R.id.titreET);
        sujetr=findViewById(R.id.sujetET);
        datet=findViewById(R.id.dateET);
        dater=findViewById(R.id.datereunion);
        heuret=findViewById(R.id.heureET);
        emplacer=findViewById(R.id.locationET);
        confirmr=findViewById(R.id.buttonconfirmreunion);
        linearLayout=findViewById(R.id.reunionlayout);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String retrievedjson = intent.getExtras().getString("mylist");

        Gson gson = new Gson();
        String empty_list = gson.toJson(new ArrayList<Profilefire>());

        ArrayList<Profilefire> listofprifiles = gson.fromJson(retrievedjson, new TypeToken<ArrayList<Profilefire>>() {}.getType());



        for(int i =0 ;i <listofprifiles.size();i++){
            Coming coming=new Coming(listofprifiles.get(i).getId(),false,false);
            userids.add(coming);
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }


        recyclerView.setHasFixedSize(false);
        FacesAdapter adapter;
        adapter=new FacesAdapter(listofprifiles);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
          layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);




        SharedPreferences prefs = getApplicationContext().getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);




                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                datet.setText(sdf.format(myCalendar1.getTime()));
            }

        };

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                myCalendar1.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar1.set(Calendar.MINUTE, minute);

                String myFormat = "HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                heuret.setText(sdf.format(myCalendar1.getTime()));



            }



        };

        datet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ReunionActivity.this, date, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        heuret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(ReunionActivity.this, time, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), true).show();

            }
        });


        confirmr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                if ((TextUtils.isEmpty(titrer.getText().toString()))) {
                    titrer.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }else if ((TextUtils.isEmpty(sujetr.getText().toString()))){
                    sujetr.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }else if ((TextUtils.isEmpty(datet.getText().toString()))){
                    datet.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }else if ((TextUtils.isEmpty(heuret.getText().toString()))){
                    heuret.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                } else if ((TextUtils.isEmpty(emplacer.getText().toString()))){
                    emplacer.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }
                if(!cancel){

                    Reunion reunion = new Reunion(userids,titrer.getText().toString(),ServerValue.TIMESTAMP,sujetr.getText().toString(),datet.getText().toString(),heuret.getText().toString(),emplacer.getText().toString());
                        reference.child("Reunions").child(residence).push().setValue(reunion);
                    int duration = Snackbar.LENGTH_LONG;
                    showSnackbar(v, "Facture envoyé vers recipient", duration);
                    finish();


                }

            }
        });

        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                linearLayout.getWindowVisibleDisplayFrame(r);

                int heightDiff = linearLayout.getRootView().getHeight() - (r.bottom - r.top);

                if (heightDiff > 244) { // if more than 100 pixels, its probably a keyboard...
                      confirmr.setVisibility(View.GONE);


                } else {
                   confirmr.setVisibility(View.VISIBLE);


                }
            }
        });




    }
    public void showSnackbar(View view, String message, int duration)
    {
        Snackbar.make(view, message, duration).show();
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
