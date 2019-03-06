package com.gst.socialcomponents.main.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
import com.gst.socialcomponents.Constants;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.base.BaseActivity;
import com.gst.socialcomponents.main.editProfile.EditProfileActivity;
import com.gst.socialcomponents.main.pickImageBase.PickImagePresenter;
import com.gst.socialcomponents.main.pickImageBase.PickImageView;
import com.gst.socialcomponents.model.Ticket;
import com.gst.socialcomponents.utils.ImageUtil;
import com.gst.socialcomponents.utils.LogUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.BreakIterator;

public class TicketActivity extends AppCompatActivity {

    FloatingActionButton addnewticket;
     private Bitmap bitmap;

     public static final int GET_FROM_GALLERY = 3;
    public static final int TAKE_PICTURE =4;

    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        addnewticket = findViewById(R.id.addNewticket);
        addnewticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showalert();

            }
        });


        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {

            reference = FirebaseDatabase.getInstance().getReference();





        }
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
               // iv.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();


            }

        }else if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK){

            Bitmap photo = (Bitmap) data.getExtras().get("data");
          //  iv.setImageBitmap(photo);

        }

    }


    void   showalert(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View adddialogview = factory.inflate(R.layout.ticketdialog, null);
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
                    Toast.makeText(TicketActivity.this, "error on title", Toast.LENGTH_SHORT).show();
                    cancel = true;
                }else if ((TextUtils.isEmpty(description))){
                    Toast.makeText(TicketActivity.this, "error on description", Toast.LENGTH_SHORT).show();
                }
                if (!cancel) {

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://syndic-spig.appspot.com/tickets");
                    StorageReference imagesRef = storageRef.child(bitmap.toString());
                    UploadTask uploadTask = imagesRef.putBytes(data);
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
                                    Ticket ticket = new Ticket(titletext, description, ServerValue.TIMESTAMP,"envoyé",photoLink);
                                     reference.child("Tickets").child(firebaseUser.getUid()).push().setValue(ticket);
                                     Log.v("linktopic",photoLink);
                                }
                            });

                        }
                    });



                 }


            }
        });
        adddialogview.findViewById(R.id.importimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);






            }
        });
        adddialogview.findViewById(R.id.takephotoimage).setOnClickListener(new View.OnClickListener() {
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
    public void uploadImage(Bitmap bitmap) {




    }







}

