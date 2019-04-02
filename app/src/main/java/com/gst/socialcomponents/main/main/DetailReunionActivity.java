package com.gst.socialcomponents.main.main;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.ReunionRetrieve;

import java.util.ArrayList;

public class DetailReunionActivity extends AppCompatActivity {

    Toolbar toolbar;
    public ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reunion);

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

        ReunionRetrieve listofreunions = gson.fromJson(retrievedjson, new TypeToken<ReunionRetrieve>() {}.getType());


 
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
