package com.gst.socialcomponents.main.main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.TicketAdapter;
import com.gst.socialcomponents.model.Ticket;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketActivity extends AppCompatActivity {

    public ActionBar actionBar;

    FloatingActionButton addnewticket;
     private Bitmap bitmap;

     public static final int GET_FROM_GALLERY = 3;
    public static final int TAKE_PICTURE =4;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private DatabaseReference reference1;

    FirebaseStorage storage;
    StorageReference storageReference;

    byte[] data;
    String url;
    String residence ;

    private List<Ticket> tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        SharedPreferences prefs = getSharedPreferences("Myprefsfile", MODE_PRIVATE);
         residence = prefs.getString("sharedprefresidence", null);


        Toolbar toolbar = findViewById(R.id.toolbarticket);
        toolbar.setTitle("Mes reclamations");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();



        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        addnewticket = findViewById(R.id.addNewticket);
        addnewticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showalert();
                Intent intent =new Intent(getApplicationContext(), SendPicticketActivity.class);
                startActivity(intent);

            }
        });


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }


        ArrayList<TicketRetrieve> tickets = new ArrayList() ;

       reference1 = FirebaseDatabase.getInstance().getReference().child("Tickets").child(residence).child(firebaseUser.getUid());
       reference1.keepSynced(true);
        reference1.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        tickets.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()) {
                             Map<String, TicketRetrieve> td = (HashMap<String, TicketRetrieve>) ds.getValue();
                              TicketRetrieve ticket = ds.getValue(TicketRetrieve.class);
                             tickets.add(ticket);
                              retrivedata(tickets);

                          }
                    }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                     }
                });






    }


    void retrivedata(ArrayList tickets){

        RecyclerView recyclerView =findViewById(R.id.recyclerView2);
        //recyclerView.setHasFixedSize(true);
        TicketAdapter adapter;
        adapter=new TicketAdapter(tickets);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            try {
                 if (bitmap != null) {
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(
                        data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();


            }

        }else if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
         }

    }
/*
    void   showalert(){

        LayoutInflater factory = LayoutInflater.from(this);
        View adddialogview = factory.inflate(R.layout.ticketdialog, null);
        final AlertDialog adddialog = new AlertDialog.Builder(this).create();
        adddialog.setView(adddialogview);
        adddialog.setCancelable(false);
        adddialogview.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;

                EditText titleet =adddialogview.findViewById(R.id.title);
                String titletext =titleet.getText().toString();
                EditText descet =adddialogview.findViewById(R.id.description);
                String description =descet.getText().toString();

                if ((TextUtils.isEmpty(titletext))) {
                     titleet.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }else if ((TextUtils.isEmpty(description))){
                    descet.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }
                if (!cancel) {
                    if(bitmap!=null) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        data = baos.toByteArray();
                        url=bitmap.toString();

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://syndic-spig.appspot.com/tickets");
                        StorageReference imagesRef = storageRef.child(url);
                        UploadTask uploadTask = imagesRef.putBytes(data);
                         ProgressDialog pd = new ProgressDialog(TicketActivity.this);
                        pd.setMessage("envoi");
                        pd.show();
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                         String photoLink = uri.toString();
                                      //  Ticket ticket = new Ticket(titletext, description,ServerValue.TIMESTAMP, "envoyé", photoLink,"empty");
                                        reference.child("Tickets").child(residence).child(firebaseUser.getUid()).push().setValue(ticket);
                                        pd.dismiss();
                                        adddialog.dismiss();
                                        bitmap=null;
                                        Toast.makeText(TicketActivity.this, "Reclmation envoyée avec sucées", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }else
                    {
                      //  Ticket ticket = new Ticket(titletext, description,ServerValue.TIMESTAMP , "envoyé", "null","empty");
                        reference.child("Tickets").child(residence).child(firebaseUser.getUid()).push().setValue(ticket);
                        adddialog.dismiss();
                        Toast.makeText(TicketActivity.this, "Reclmation envoyée avec sucées", Toast.LENGTH_SHORT).show();
                    }
                    }
            }
        });
        adddialogview.findViewById(R.id.joindre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);



            }
        });
        adddialogview.findViewById(R.id.prendre).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent cameraIntent = new Intent(
                            android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_PICTURE);
                } catch (ActivityNotFoundException ex) {

                }
            }
        });
        adddialogview.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adddialog.hide();
            }
        });

        adddialog.show();
    }
    */
    public void uploadImage(Bitmap bitmap) {
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

