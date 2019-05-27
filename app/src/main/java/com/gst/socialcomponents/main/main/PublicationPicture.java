package com.gst.socialcomponents.main.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gst.socialcomponents.R;

public class PublicationPicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publication_picture2);
        ImageView imageView=findViewById(R.id.imageView4);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        Glide.with(imageView.getContext()).load(url).into(imageView);


    }
}
