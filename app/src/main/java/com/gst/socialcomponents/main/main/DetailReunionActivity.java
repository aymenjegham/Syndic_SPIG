package com.gst.socialcomponents.main.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.FacesAdapter;
import com.gst.socialcomponents.adapters.ReunionmembersAdapter;
import com.gst.socialcomponents.model.Coming;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.ReunionRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;

import java.util.ArrayList;

public class DetailReunionActivity extends AppCompatActivity {

    Toolbar toolbar;
    public ActionBar actionBar;
    public TextView titre;
    public TextView timestamp;
    public TextView sujet;
    public TextView date;
    public TextView heure;
    public TextView location;
    public RecyclerView recyclerView;
    public ArrayList<Coming> reunionists=new ArrayList<>();
    Context cxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reunion);

      titre=findViewById(R.id.titreacttv);
       timestamp=findViewById(R.id.detailtimestamptv);
       sujet=findViewById(R.id.detailsujettv);
       date=findViewById(R.id.detaildatetv);
       heure=findViewById(R.id.detailheuretv);
        location=findViewById(R.id.detaillocationtv);
        recyclerView=findViewById(R.id.rvdetailactivity);

        toolbar = findViewById(R.id.toolbardetailreunion);
        toolbar.setTitle("Moderateur");
        toolbar.setBackgroundColor(0xffB22222);

        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String retrievedjson = intent.getExtras().getString("myarray");




        Gson gson = new Gson();
        String empty_list = gson.toJson(new ArrayList<ReunionRetrieve>());

        ReunionRetrieve reuniondetails = gson.fromJson(retrievedjson, new TypeToken<ReunionRetrieve>() {}.getType());
        reunionists =reuniondetails.getUserids();
        Long timelong=reuniondetails.getTimestamp();

        titre.setText(reuniondetails.getTitre());
        CharSequence datetoset = FormatterUtil.getRelativeTimeSpanString(cxt, timelong);
        timestamp.setText(datetoset);
        sujet.setText(reuniondetails.getSujet());
        date.setText(reuniondetails.getDate());
        heure.setText(reuniondetails.getHeure());
        location.setText(reuniondetails.getLocation());

        Log.v("reunionistlist",reunionists.size()+"  ");



        recyclerView.setHasFixedSize(false);
        ReunionmembersAdapter adapter;
        adapter=new ReunionmembersAdapter(reunionists);
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
