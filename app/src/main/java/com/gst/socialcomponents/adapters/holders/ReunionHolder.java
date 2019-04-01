package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.ReunionRetrieve;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;

public class ReunionHolder extends RecyclerView.ViewHolder {


    public TextView titre;
    public TextView timestamp;
    public TextView sujet;
    public TextView date;
    public TextView heure;
    public TextView location;




    public ReunionHolder(View reunionCard) {
        super(reunionCard);

        this.titre=itemView.findViewById(R.id.titretv);
        this.timestamp=itemView.findViewById(R.id.timestamptv);
        this.sujet=itemView.findViewById(R.id.sujettv);
        this.date=itemView.findViewById(R.id.datetv);
        this.heure=itemView.findViewById(R.id.heuretv);
        this.location=itemView.findViewById(R.id.locationtv);


    }



    public void updateUI(ReunionRetrieve reunionRetrieve){
        String titleString= reunionRetrieve.getTitre();
        String descriptionString=reunionRetrieve.getSujet();
        String datestring=reunionRetrieve.getDate();
        String heurestring=reunionRetrieve.getHeure();
        Long timelong=reunionRetrieve.getTimestamp();
        String locationstring =reunionRetrieve.getLocation();

        titre.setText(titleString);
        timestamp.setText(timelong.toString());
        sujet.setText(descriptionString);
        date.setText(datestring);
        heure.setText(heurestring);
        location.setText(locationstring);


    }
}
