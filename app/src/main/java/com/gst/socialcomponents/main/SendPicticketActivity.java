package com.gst.socialcomponents.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.CustomFacturesMonthsAdapter;
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.main.main.ToolActivity;
import com.gst.socialcomponents.model.Factureitemdata;
import com.gst.socialcomponents.model.InfoSyndic;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.SoldeAppartement;
import com.gst.socialcomponents.model.Ticket;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.gst.socialcomponents.main.main.TicketActivity.GET_FROM_GALLERY;
import static com.gst.socialcomponents.main.main.TicketActivity.TAKE_PICTURE;

public class SendPicticketActivity extends AppCompatActivity {

    Button addbtn, cnclbtn;
    LinearLayout joindrebtn, prendrebtn;

    private Bitmap bitmap;

    public static final int GET_FROM_GALLERY = 3;
    public static final int TAKE_PICTURE = 4;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private DatabaseReference reference2;

    FirebaseStorage storage;
    StorageReference storageReference;

    byte[] data;
    String url;
    String residence;
    ImageView ivtick;
    private APIService mAPIService;
    private Integer idResidence;
    private Integer idAppartement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_picticket);
        addbtn = findViewById(R.id.add);
        cnclbtn = findViewById(R.id.cancel);
        joindrebtn = findViewById(R.id.joindre);
        prendrebtn = findViewById(R.id.prendre);
        ivtick = findViewById(R.id.imageViewTicket);

        SharedPreferences prefs = getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);

        mAPIService = ApiUtils.getAPIService();
        mAPIService.getNumChantier(residence).enqueue(new Callback<NumChantier>() {

            @Override
            public void onResponse(Call<NumChantier> call, Response<NumChantier> response) {
                idResidence=Integer.valueOf(response.body().getCbmarq());
                reference2 = FirebaseDatabase.getInstance().getReference().child("profiles").child(firebaseUser.getUid()).child("numresidence");
                reference2.keepSynced(true);
                reference2.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String appart = dataSnapshot.getValue().toString();
                                mAPIService = ApiUtils.getAPIService();
                                getcbmarq(appart,idResidence);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }

            @Override
            public void onFailure(Call<NumChantier> call, Throwable t) {
                Log.v("gettingnumchantier", "fail");

            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();
        }


        cnclbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        joindrebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);
            }
        });

        prendrebtn.setOnClickListener(new View.OnClickListener() {
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


    }








    public void getcbmarq(String appartId, Integer idResidence){
            mAPIService.getNumOfAppartements(appartId).enqueue(new Callback<NumAppart>() {
        @Override
        public void onResponse (Call < NumAppart > call, Response < NumAppart > response){

            idAppartement=Integer.valueOf(response.body().getCbmarq());

            addbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean cancel = false;

                    EditText titleet = findViewById(R.id.title);
                    String titletext = titleet.getText().toString();
                    EditText descet = findViewById(R.id.description);
                    String description = descet.getText().toString();

                    if ((TextUtils.isEmpty(titletext))) {
                        titleet.setError(getApplicationContext().getString(R.string.error_field_required));
                        cancel = true;
                    } else if ((TextUtils.isEmpty(description))) {
                        descet.setError(getApplicationContext().getString(R.string.error_field_required));
                        cancel = true;
                    }
                    if (!cancel) {
                        if (bitmap != null) {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            data = baos.toByteArray();
                            url = bitmap.toString();

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            StorageReference storageRef = storage.getReferenceFromUrl("gs://syndic-spig.appspot.com/tickets");
                            StorageReference imagesRef = storageRef.child(url);
                            UploadTask uploadTask = imagesRef.putBytes(data);
                            ProgressDialog pd = new ProgressDialog(SendPicticketActivity.this);
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
                                            Ticket ticket = new Ticket(titletext, description, ServerValue.TIMESTAMP, 0, photoLink, "empty",idResidence,idAppartement);
                                            reference.child("Tickets").child(residence).child(firebaseUser.getUid()).push().setValue(ticket);
                                            pd.dismiss();
                                            bitmap = null;
                                            Toast.makeText(SendPicticketActivity.this, "Reclmation envoyée avec sucées", Toast.LENGTH_SHORT).show();
                                            finish();

                                        }
                                    });
                                }
                            });
                        } else {
                            Ticket ticket = new Ticket(titletext, description, ServerValue.TIMESTAMP, 0, "null", "empty",idResidence,idAppartement);
                            reference.child("Tickets").child(residence).child(firebaseUser.getUid()).push().setValue(ticket);
                            Toast.makeText(SendPicticketActivity.this, "Reclmation envoyée avec sucées", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            });

        }
        @Override
        public void onFailure (Call < NumAppart > call, Throwable t){
            Toast.makeText(SendPicticketActivity.this, "Connexion au serveur échouée", Toast.LENGTH_SHORT).show();



        }
    });
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

                Glide.with(getApplicationContext()).load(bitmap).into(ivtick);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();


            }

        }else if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
            Glide.with(getApplicationContext()).load(bitmap).into(ivtick);
        }

    }
    }

