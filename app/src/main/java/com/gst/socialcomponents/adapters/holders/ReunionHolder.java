package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.Coming;
import com.gst.socialcomponents.model.ReunionRetrieve;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;

import java.util.ArrayList;

public class ReunionHolder extends RecyclerView.ViewHolder {


    public TextView titre;
    public TextView timestamp;
    public TextView sujet;
    public TextView date;
    public TextView heure;
    public TextView location;
    public Context cxt;
    public FloatingActionButton accept;
    public FloatingActionButton refuse;
    private FirebaseUser firebaseUser;
    public  Context context;
    public FloatingActionButton partant;
    public FloatingActionButton refusant;




    public ReunionHolder(View reunionCard) {
        super(reunionCard);

        this.titre=itemView.findViewById(R.id.titretv);
        this.timestamp=itemView.findViewById(R.id.timestamptv);
        this.sujet=itemView.findViewById(R.id.sujettv);
        this.date=itemView.findViewById(R.id.datetv);
        this.heure=itemView.findViewById(R.id.heuretv);
        this.location=itemView.findViewById(R.id.locationtv);
        this.accept=itemView.findViewById(R.id.buttonaccept);
        this.refuse=itemView.findViewById(R.id.buttonrefuse);
        this.partant=itemView.findViewById(R.id.floatingActionButton2);
        this.refusant=itemView.findViewById(R.id.floatingActionButton3);



    }



    public void updateUI(ReunionRetrieve reunionRetrieve){
        String titleString= reunionRetrieve.getTitre();
        String descriptionString=reunionRetrieve.getSujet();
        String datestring=reunionRetrieve.getDate();
        String heurestring=reunionRetrieve.getHeure();
        Long timelong=reunionRetrieve.getTimestamp();
        String locationstring =reunionRetrieve.getLocation();


        titre.setText(titleString);
        sujet.setText(descriptionString);
        date.setText(datestring);
        heure.setText(heurestring);
        location.setText(locationstring);


        CharSequence date = FormatterUtil.getRelativeTimeSpanString(cxt, timelong);
        timestamp.setText(date);

        ArrayList<ReunionRetrieve> reunions = new ArrayList() ;
        ArrayList<Coming> members=new ArrayList<>();
        members=reunionRetrieve.getUserids();
         firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        for(int i=0;i<members.size();i++){

            if(members.get(i).getUsersid() .equals(firebaseUser.getUid())){
                 if(members.get(i).getSeen()){
                    accept.setEnabled(false);
                    refuse.setEnabled(false);
                    accept.setAlpha(.5f);
                    refuse.setAlpha(.5f);
                    accept.hide();
                    refuse.hide();
                    if(members.get(i).getState()){
                        partant.show();
                    }else{
                        refusant.show();
                    }

                }
            }
        }





    }
}
