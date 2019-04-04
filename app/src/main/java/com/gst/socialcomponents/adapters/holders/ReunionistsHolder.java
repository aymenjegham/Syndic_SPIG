package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.Coming;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;

public class ReunionistsHolder extends RecyclerView.ViewHolder {



    private TextView name;
    private TextView numresidence;
    private TextView residence;
    private TextView mobileno;
    Context cxt;
    private ImageView photoImageView;
    public FloatingActionButton partant;
    public FloatingActionButton refusant;
    DatabaseReference reference ;
    private FirebaseUser firebaseUser;



    public ReunionistsHolder(View memberCard) {
        super(memberCard);
        this.name=itemView.findViewById(R.id.nameTextViewdetail);
        this.numresidence=itemView.findViewById(R.id.matriculeresidencedetail );
        this.residence= itemView.findViewById(R.id.residencedetail);
         this.mobileno=itemView.findViewById(R.id.mobilenodetail);
        this.photoImageView=itemView.findViewById(R.id.photoImageViewdetail);

        this.partant=itemView.findViewById(R.id.buttonaccepted);
        this.refusant=itemView.findViewById(R.id.buttonrefused);
    }

    public void updateUI(Profilefire profile, Coming coming){

        String urlpath =profile.getPhotoUrl();
        Glide.with(photoImageView).load(urlpath).into(photoImageView);
        name.setText(profile.getUsername());
        numresidence.setText(profile.getNumresidence());
        residence.setText(profile.getResidence());
        mobileno.setText(profile.getMobile());

                if(coming.getSeen()){
                    if (coming.getState()){
                        partant.show();
                    }else {
                        refusant.show();
                    }
                }
    }
}
