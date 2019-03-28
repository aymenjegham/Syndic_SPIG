package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;

public class Faceholder extends RecyclerView.ViewHolder {

    private ImageView photoImageView;


    public Faceholder(View profilecard) {
        super(profilecard);

        this.photoImageView=itemView.findViewById(R.id.photoprofilefc);



    }

    public void updateUI(Profilefire profilefire){
        String urlpath =profilefire.getPhotoUrl();
        Glide.with(photoImageView.getContext()).load(urlpath).into(photoImageView);



    }
}
