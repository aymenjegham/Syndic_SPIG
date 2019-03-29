package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.Facture;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.utils.FormatterUtil;

public class FactureHolder extends RecyclerView.ViewHolder {


    TextView titletv,montant,datede,datejusqua,datelimit,stateTv,time;
    Boolean statut;
    Context cxt;
    ImageView stateImage;


    public FactureHolder(View facturecard) {
        super(facturecard);

        this.titletv=itemView.findViewById(R.id.title);
        this.datede=itemView.findViewById(R.id.periodede);
        this.datejusqua=itemView.findViewById(R.id.periodejusqua);
        this.datelimit=itemView.findViewById(R.id.limitdate);
        this.stateTv=itemView.findViewById(R.id.stateTextView);
        this.montant=itemView.findViewById(R.id.montantTv);
        this.time=itemView.findViewById(R.id.dateTextViewpanel);
        this.stateImage=itemView.findViewById(R.id.stateImageViewpanel);






    }

    public void updateUI(Facture facture){

        String titre =facture.getTitle();
        String datedee=facture.getDatede();
        String datejusqaa=facture.getDatejusqua();
        String datelimite=facture.datelimite;
        statut=facture.getStatut();
        String montantapayer=facture.getMontant();
        Long timelong=facture.getTimestamp();


        titletv.setText(titre);
        datede.setText(datedee);
        datejusqua.setText(datejusqaa);
        datelimit.setText(datelimite);

        if(!statut){
            stateTv.setTextColor(Color.RED);
            stateTv.setText("Non payé");
            montant.setText(montantapayer);

    }else if(statut){
            stateTv.setTextColor(Color.BLUE);
            stateTv.setText("Payé");
            montant.setText(montantapayer);
            montant.setPaintFlags(montant.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            stateImage.setImageResource(R.drawable.ic_done);

        }

        CharSequence date = FormatterUtil.getRelativeTimeSpanString(cxt, timelong);

        time.setText(date);


    }
}
