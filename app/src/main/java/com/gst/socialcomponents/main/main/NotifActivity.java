package com.gst.socialcomponents.main.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.NotifAdapter;
import com.gst.socialcomponents.main.editProfile.EditProfileActivity;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.room.DatabaseClient;
import com.gst.socialcomponents.room.Notif;

import java.util.Collections;
import java.util.List;

public class NotifActivity extends AppCompatActivity {





    Toolbar toolbar ;
    public Boolean typeuser;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    public ActionBar actionBar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);
        recyclerView =findViewById(R.id.recyclerViewnotif);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.toolbarnot);
        toolbar.setTitle("Mes notifications");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        getNotifs();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference("profiles").child(firebaseUser.getUid());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Profilefire profile = dataSnapshot.getValue(Profilefire.class);
                    typeuser=profile.isType();

                    if(typeuser== true){
                        changesetup();
                    }
                    if(typeuser== false){
                        changesetuptodefault();
                    }
                    if (!profile.isActive()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try{
                                    showalert();
                                    Looper.loop();
                                    Looper.myLooper().quit();

                                }catch (WindowManager.BadTokenException e){

                                }
                            }
                        });
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });

        }
    }

    private void getNotifs() {
        class GetTasks extends AsyncTask<Void, Void, List<Notif>> {

            @Override
            protected List<Notif> doInBackground(Void... voids) {
                List<Notif> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .notifDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Notif> notifs) {
                super.onPostExecute(notifs);


                Collections.reverse(notifs);
                NotifAdapter adapter = new NotifAdapter(NotifActivity.this, notifs);
                recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
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

    private void changesetup() {

        toolbar.setTitle("Notifications Moderateur");
        toolbar.setBackgroundColor(0xffB22222);
    }
    private void changesetuptodefault() {

        toolbar.setTitle("Syndic IG");
        toolbar.setBackgroundColor(getResources().getColor(R.color.send_button_color));

    }

    void   showalert(){
        new AlertDialog.Builder(NotifActivity.this,R.xml.styles)
                .setTitle("En attente de vérification")
                .setMessage("Votre adhésion est en attente de confirmation d'un modérateur" +
                        ",vous serez notifié de l'activation de votre compte")
                .setCancelable(false)
                .setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent(NotifActivity.this,GalleryActivity.class);
                        startActivity(intent);
                    }
                })
                .setNeutralButton("Modifier votre profile", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent =new Intent(NotifActivity.this, EditProfileActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Quitter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //  Intent intent1 = new Intent(Intent.ACTION_MAIN);
                        // intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        // intent1.addCategory(Intent.CATEGORY_HOME);
                        // startActivity(intent1);

                        finishAffinity();

                    }
                })
                .show();
    }






}
