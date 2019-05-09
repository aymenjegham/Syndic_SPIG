package com.gst.socialcomponents.main.main;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.ortiz.touchview.TouchImageView;

public class TicketDetailActivity extends AppCompatActivity {

    EditText commentEt;
    Button submitBtn;
    private Task<Void> reference2;
    String residence ;
    Toolbar toolbar;
    TabLayout tablayout;
    public ActionBar actionBar;



    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
       TouchImageView img = (TouchImageView) findViewById(R.id.imageViewdetail);
       commentEt=findViewById(R.id.commentEditText);
       submitBtn=findViewById(R.id.sendButton);




        toolbar = findViewById(R.id.toolbarticketmoddetail);
        toolbar.setTitle("Moderateur");
        toolbar.setBackgroundColor(0xffB22222);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences prefs = getSharedPreferences("Myprefsfile", MODE_PRIVATE);
        residence = prefs.getString("sharedprefresidence", null);


        String ImageUrl;
        String UserKey;
        String TicketKey;


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                ImageUrl= null;
                UserKey= null;
                TicketKey= null;

            } else {
                ImageUrl= extras.getString("ImageUrl");
                UserKey= extras.getString("UserKey");
                TicketKey= extras.getString("TicketKey");


            }
        } else {
            ImageUrl= (String) savedInstanceState.getSerializable("ImageUrl");
            UserKey= (String) savedInstanceState.getSerializable("UserKey");
            TicketKey= (String) savedInstanceState.getSerializable("TicketKey");



        }


        if(ImageUrl.equals("null")){
            ProgressDialog pd = new ProgressDialog(TicketDetailActivity.this);
            pd.setMessage("chargement d'image");
            pd.show();
            pd.setCancelable(false);

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_gallery)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(img.getContext())
                    .load(R.drawable.ic_gallery)
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pd.dismiss();
                            img.setImageResource(R.drawable.ic_gallery);

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pd.dismiss();

                            return false;
                        }

                    })
                    .into(img);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                img.setImageResource(R.drawable.ic_gallery);

            }
        });}
        else if(!ImageUrl.equals("null")){
             ProgressDialog pd = new ProgressDialog(TicketDetailActivity.this);
            pd.setMessage("chargement d'image");
            pd.show();

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.ic_gallery)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH);

            Glide.with(img.getContext())
                    .load(ImageUrl)
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {

                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                             pd.dismiss();
                            img.setImageResource(R.drawable.ic_gallery);
                             return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                             pd.dismiss();

                            return false;
                        }
                    })
                    .into(img);
            pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    img.setImageResource(R.drawable.ic_gallery);

                }
            });

        }




        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancel = false;
                String comment = commentEt.getText().toString();


                if ((TextUtils.isEmpty(comment))) {
                    commentEt.setError(getApplicationContext().getString(R.string.error_field_required));
                    cancel = true;
                }
                if (!cancel) {

                    reference2 = FirebaseDatabase.getInstance().getReference().child("Tickets").child(residence).child(UserKey).child(TicketKey).child("comment").
                            setValue(commentEt.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finish();
                            Toast.makeText(TicketDetailActivity.this, "Commentaire ajoutée avec succée", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }
            }
        });


    }
}