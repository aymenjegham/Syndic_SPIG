package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.DataReunion;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.utils.FormatterUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Response;

public class Meetingholder extends RecyclerView.ViewHolder {

    private TextView object;
    private TextView sujet;
    private TextView datedebut;
    private TextView datefin;
    private TextView emplacement;
    private TextView datecreation;
    private Context cxt;
    public FloatingActionButton accept;




    public Meetingholder(View profilecard) {
        super(profilecard);

        this.object=itemView.findViewById(R.id.objettv);
        this.sujet=itemView.findViewById(R.id.sujettv);
        this.datedebut=itemView.findViewById(R.id.heuretv);
        this.datefin=itemView.findViewById(R.id.datetv);
        this.emplacement=itemView.findViewById(R.id.locationtv);
        this.datecreation=itemView.findViewById(R.id.timestamptv);
        this.accept=itemView.findViewById(R.id.buttonaccept);






    }

    public void updateUI(Response<DataReunion> reunionResponse, Integer iduser){
        String reunionobject =reunionResponse.body().r_objet;
        String reunionsujet=reunionResponse.body().r_description;
        String debutreunion=reunionResponse.body().r_datedebut;
        String finreunion=reunionResponse.body().r_datefin;
        String emplacemen=reunionResponse.body().r_emplacement;
        String datecree=reunionResponse.body().cbcreation;
        Integer reunionid=reunionResponse.body().cbmarq;

        object.setText(reunionobject);
        sujet.setText(reunionsujet);

        emplacement.setText(emplacemen);
        datecreation.setText(datecree);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date datedebu   = format.parse ( debutreunion );
            Date datedfin   = format.parse ( finreunion );

            CharSequence timebegin = FormatterUtil.getRelativeTimeSpanString(cxt,datedebu.getTime());
            CharSequence timeend = FormatterUtil.getRelativeTimeSpanString(cxt,datedfin.getTime());


            datedebut.setText(datedebu.getHours()+"h"+datedebu.getMinutes()+"mn ,"+timebegin);
            datefin.setText(datedfin.getHours()+"h"+datedfin.getMinutes()+"mn ,"+timeend);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Log.v("checkingtosend",reunionid+"  "+iduser);
            }
        });


    }
}
