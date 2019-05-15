package com.gst.socialcomponents.main.main;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

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
import com.gst.socialcomponents.data.remote.APIService;
import com.gst.socialcomponents.data.remote.ApiUtils;
import com.gst.socialcomponents.data.remote.ResultObject;
import com.gst.socialcomponents.data.remote.VideoInterface;
import com.gst.socialcomponents.managers.PostManager;
import com.gst.socialcomponents.model.NumAppart;
import com.gst.socialcomponents.model.NumChantier;
import com.gst.socialcomponents.model.Post;
import com.gst.socialcomponents.model.Ticket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SendPicticketActivity extends AppCompatActivity {

    Button addbtn, cnclbtn;
    LinearLayout joindrebtn,joindrevid, prendrebtn,prendrevid;

    private Bitmap bitmap;

    public ActionBar actionBar;
    public static final int GET_FROM_GALLERY = 3;
    public static final int TAKE_PICTURE = 4;
    public static final int TAKE_VIDEO = 5;


    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private DatabaseReference reference2;

    FirebaseStorage storage;
    StorageReference storageReference;

    byte[] data;
    byte[] convertVideoToBytes;
    String url;
    String residence;
    ImageView ivtick;
    private APIService mAPIService;
    private Integer idResidence;
    private Integer idAppartement;
    CheckBox checkbxpub;

    protected PostManager postManager;
    String photoLink;
    VideoView videoView;

    Uri selectedMediaUri;
    private static final String SERVER_PATH = "http://syndicspig.gloulougroupe.com";
    private String pathToStoredVideo;
    String link;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_picticket);
        addbtn = findViewById(R.id.add);
        cnclbtn = findViewById(R.id.cancel);
        joindrebtn = findViewById(R.id.joindre);
        prendrebtn = findViewById(R.id.prendre);
        ivtick = findViewById(R.id.imageViewTicket);
        checkbxpub=findViewById(R.id.cbpublic);
        joindrevid=findViewById(R.id.joindrevideo);
        videoView=findViewById(R.id.videoView);
        prendrevid=findViewById(R.id.prendrevideo);

        Toolbar toolbar = findViewById(R.id.toolbarsendticket);
        toolbar.setTitle("Envoi reclamation");
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        postManager = PostManager.getInstance(getApplicationContext());


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

        joindrevid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
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

        prendrevid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent cameraIntent = new Intent(
                            MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(cameraIntent, TAKE_VIDEO);
                } catch (ActivityNotFoundException ex) {

                }
            }
        });


    }
    public void getcbmarq(String appartId, Integer idResidence){


        mAPIService.getNumChantier(residence).enqueue(new Callback<NumChantier>() {
            @Override
            public void onResponse(Call<NumChantier> call, Response<NumChantier> response) {
                Integer numchantier=Integer.valueOf(response.body().getCbmarq());

                mAPIService.getNumOfAppartements(appartId,numchantier).enqueue(new Callback<NumAppart>() {
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
                                        StorageReference storageRef = storage.getReferenceFromUrl("gs://syndic-spig.appspot.com/images");
                                        StorageReference imagesRef = storageRef.child(url);
                                        UploadTask uploadTask = imagesRef.putBytes(data);
                                        ProgressDialog pd = new ProgressDialog(SendPicticketActivity.this);
                                        pd.setMessage("envoi");
                                        pd.show();
                                        uploadTask.addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                Toast.makeText(SendPicticketActivity.this, "Connection a échoué", Toast.LENGTH_SHORT).show();
                                                pd.dismiss();
                                            }
                                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                Task<Uri> task = taskSnapshot.getMetadata().getReference().getDownloadUrl();

                                                String task2=taskSnapshot.getMetadata().getReference().getName();

                                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {

                                                        photoLink = uri.toString();
                                                        Ticket ticket = new Ticket(titletext, description, ServerValue.TIMESTAMP, 0, photoLink, "empty",idResidence,idAppartement,0);
                                                        reference.child("Tickets").child(residence).child(firebaseUser.getUid()).push().setValue(ticket);
                                                        pd.dismiss();
                                                        bitmap = null;
                                                        Toast.makeText(SendPicticketActivity.this, "Reclmation envoyée avec sucées", Toast.LENGTH_SHORT).show();
                                                        finish();
                                                        if(checkbxpub.isChecked()){

                                                            Post post = new Post();
                                                            post.setTitle(titletext);
                                                            post.setModerator("false");
                                                            post.setIsvideo(false);
                                                            post.setResidence(residence);
                                                            post.setDescription(description);
                                                            post.setAuthorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                            post.setImageTitle(task2);
                                                            reference.child("posts").push().setValue(post);

                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    } else if(bitmap ==null && selectedMediaUri ==null) {
                                        Ticket ticket = new Ticket(titletext, description, ServerValue.TIMESTAMP, 0, "null", "empty",idResidence,idAppartement,2);
                                        reference.child("Tickets").child(residence).child(firebaseUser.getUid()).push().setValue(ticket);
                                        Toast.makeText(SendPicticketActivity.this, "Reclamation envoyée avec sucées", Toast.LENGTH_SHORT).show();


                                        finish();
                                    }


                                    if(selectedMediaUri !=null && bitmap ==null){


                                         pathToStoredVideo = getRealPathFromURIPath(selectedMediaUri, SendPicticketActivity.this);
                                        uploadVideoToServer(pathToStoredVideo,titletext,description);


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
            public void onFailure(Call<NumChantier> call, Throwable t) {

            }
        });

}


    public boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                 return true;
            } else {

                 ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
             return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){

        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void uploadVideoToServer(String pathToVideoFile,String titletext,String description){
        File videoFile = new File(pathToVideoFile);
        RequestBody videoBody = RequestBody.create(MediaType.parse("video/*"), videoFile);
        MultipartBody.Part vFile = MultipartBody.Part.createFormData("video", videoFile.getName(), videoBody);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        VideoInterface vInterface = retrofit.create(VideoInterface.class);
        Call<ResultObject>  serverCom = vInterface.uploadVideoToServer(vFile);
        checkPermissions();
        ProgressDialog pd = new ProgressDialog(SendPicticketActivity.this);
        pd.setMessage("envoi");
        pd.show();
        serverCom.enqueue(new Callback<ResultObject>() {
            @Override
            public void onResponse(Call<ResultObject> call, Response<ResultObject> response) {
                ResultObject result = response.body();
              if(!TextUtils.isEmpty(result.getSuccess())){
                     Log.v("checkingsendingvideo",  response.body().getSuccess());
                     link=response.body().getSuccess();
                  Ticket ticket = new Ticket(titletext, description, ServerValue.TIMESTAMP, 0, link, "empty",idResidence,idAppartement,1);

                  reference.child("Tickets").child(residence).child(firebaseUser.getUid()).push().setValue(ticket);
                  Toast.makeText(SendPicticketActivity.this, "Reclmation envoyée avec sucées", Toast.LENGTH_SHORT).show();
                  if(checkbxpub.isChecked()){

                      Post post = new Post();
                      post.setTitle(titletext);
                      post.setModerator("false");
                      post.setIsvideo(true);
                      post.setResidence(residence);
                      post.setDescription(description);
                      post.setAuthorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                      post.setImageTitle(link);
                      reference.child("posts").push().setValue(post);
                  }
                  finish();
                  pd.dismiss();

               }
            }
            @Override
            public void onFailure(Call<ResultObject> call, Throwable t) {
                Log.v("checkingsendingvideo", "Error message " + t.getMessage());
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

                videoView.setVisibility(View.GONE);
                ivtick.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(bitmap).into(ivtick);

                 selectedMediaUri = data.getData();
                if (selectedMediaUri.toString().contains("video")) {
                     videoView.setVisibility(View.VISIBLE);
                    videoView.setVideoURI(selectedMediaUri);
                    videoView.start();

                }



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();


            }

        }else if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK){
            bitmap = (Bitmap) data.getExtras().get("data");
            videoView.setVisibility(View.GONE);
            ivtick.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext()).load(bitmap).into(ivtick);
        }

        else if (requestCode == TAKE_VIDEO && resultCode == Activity.RESULT_OK){

            Toast.makeText(this, "video capturé", Toast.LENGTH_SHORT).show();
            selectedMediaUri = data.getData();
            if (selectedMediaUri.toString().contains("video")) {
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoURI(selectedMediaUri);
                videoView.start();

            }
    }

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

