package com.gst.socialcomponents.main.main;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.FacesAdapter;
import com.gst.socialcomponents.model.Facture;
import com.gst.socialcomponents.model.Factureenvoi;
import com.gst.socialcomponents.model.Profilefire;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class FacturationActivity extends AppCompatActivity {

    ArrayList<Profilefire> profilestoinvoice= new ArrayList() ;
    Toolbar toolbar;
    public ActionBar actionBar;
    EditText depuis;
    EditText jusqua;
    EditText datelimit;
    final Calendar myCalendar1= Calendar.getInstance();
    final Calendar myCalendar2= Calendar.getInstance();
    final Calendar myCalendar3= Calendar.getInstance();
    FirebaseUser firebaseUser;
    DatabaseReference reference,reference1;
    Button confirmbutton;
    EditText titre;
    EditText montant;
    LinearLayout linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturation);
        depuis=  findViewById(R.id.deouis);
        jusqua=  findViewById(R.id.titreET);
        datelimit=  findViewById(R.id.datelimit);
        titre=findViewById(R.id.editText);
        montant=findViewById(R.id.editText2);
        linearLayout=findViewById(R.id.linearLayoutfacturation);


        confirmbutton=findViewById(R.id.buttonfacture);

        RecyclerView recyclerView = findViewById(R.id.rvfaces);

        toolbar = findViewById(R.id.toolbarfacturation);
        toolbar.setTitle("Moderateur");
        toolbar.setBackgroundColor(0xffB22222);

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

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }
/*
        for (int i=0 ;i<listofprifiles.size();i++){
            Log.v("idisofuser",listofprifiles.get(i).getId()+"  "+firebaseUser.getUid());

            if(listofprifiles.get(i).getId().equals(firebaseUser.getUid())){
                Log.v("idisofuser",listofprifiles.get(i).getId());
                listofprifiles.remove(i);

            }
        }
        */

        recyclerView.setHasFixedSize(false);
        FacesAdapter adapter;
        adapter=new FacesAdapter(listofprifiles);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);







        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                 myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);




                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                depuis.setText(sdf.format(myCalendar1.getTime()));
             }

        };


        DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                jusqua.setText(sdf.format(myCalendar2.getTime()));
            }

        };

        DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar3.set(Calendar.YEAR, year);
                myCalendar3.set(Calendar.MONTH, monthOfYear);
                myCalendar3.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "dd/MM/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                datelimit.setText(sdf.format(myCalendar3.getTime()));
            }

        };

        depuis.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(FacturationActivity.this, date, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        jusqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FacturationActivity.this, date2, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        datelimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FacturationActivity.this, date3, myCalendar3.get(Calendar.YEAR), myCalendar3.get(Calendar.MONTH), myCalendar3.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        confirmbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                if ((TextUtils.isEmpty(titre.getText().toString()))) {
                    titre.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }else if ((TextUtils.isEmpty(montant.getText().toString()))){
                    montant.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }else if ((TextUtils.isEmpty(depuis.getText().toString()))){
                    depuis.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }else if ((TextUtils.isEmpty(jusqua.getText().toString()))){
                    jusqua.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                } else if ((TextUtils.isEmpty(datelimit.getText().toString()))){
                    datelimit.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }
                if(!cancel){
                    for(int i=0;i<listofprifiles.size();i++) {

                        Factureenvoi factureenvoi =new Factureenvoi(titre.getText().toString(),montant.getText().toString(),ServerValue.TIMESTAMP,depuis.getText().toString(),jusqua.getText().toString(),datelimit.getText().toString(),false);

                        Log.v("etfieldstestlog",listofprifiles.get(i).getResidence()+"done"+listofprifiles.get(i).getId()+"  ");

                        reference.child("Frais").child(listofprifiles.get(i).getResidence()).child(listofprifiles.get(i).getId()).push().setValue(factureenvoi);
                        int duration = Snackbar.LENGTH_LONG;
                        showSnackbar(v, "Facture envoyé vers recipient", duration);
                        finish();



                     }
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
                    confirmbutton.setVisibility(View.GONE);


                } else {
                    confirmbutton.setVisibility(View.VISIBLE);


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
