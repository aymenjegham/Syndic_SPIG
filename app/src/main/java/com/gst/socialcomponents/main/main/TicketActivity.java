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
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.gst.socialcomponents.Constants;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.base.BaseActivity;
import com.gst.socialcomponents.main.editProfile.EditProfileActivity;
import com.gst.socialcomponents.main.pickImageBase.PickImagePresenter;
import com.gst.socialcomponents.main.pickImageBase.PickImageView;
import com.gst.socialcomponents.utils.ImageUtil;
import com.gst.socialcomponents.utils.LogUtil;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.BreakIterator;

public class TicketActivity extends AppCompatActivity {

    FloatingActionButton addnewticket;
    ImageView iv;



     public static final int GET_FROM_GALLERY = 3;
    public static final int TAKE_PICTURE =4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);



        iv=findViewById(R.id.imageView2);
        iv.setImageResource(R.drawable.btn_google_dark_disabled_xhdpi);

        addnewticket = findViewById(R.id.addNewticket);
        addnewticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showalert();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     /*   if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                 e.printStackTrace();
            } catch (IOException e) {
                 e.printStackTrace();
            }
        } */
        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePath = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePath,
                    null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));






        }else if (requestCode == TAKE_PICTURE && resultCode == Activity.RESULT_OK){

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(photo);

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
                 }


            }
        });
        adddialogview.findViewById(R.id.importimage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
             //   Intent intent = new Intent();
               // intent.setType("image/*");
                //intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, "Select Picture"),GET_FROM_GALLERY);

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





}

