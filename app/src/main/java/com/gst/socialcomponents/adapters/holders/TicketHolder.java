package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.collection.LLRBNode;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;

import static com.gst.socialcomponents.R.drawable.com_facebook_button_background;

public class TicketHolder extends RecyclerView.ViewHolder {



    private TextView title;
    private TextView description;
    private ImageView photolink;
    private TextView state;
    private TextView matricule;
    private TextView time;
    Context cxt;
    private ImageView stateimageview;
     private  TextView comment;
    private  TextView commenttv;




    public TicketHolder(View ticketCard) {
        super(ticketCard);


        this.title=(TextView)itemView.findViewById(R.id.title);
        this.description=(TextView)itemView.findViewById(R.id.description );
        this.photolink=(ImageView) itemView.findViewById(R.id.photo);
        this.state=(TextView)itemView.findViewById(R.id.stateTextView);
        this.matricule=(TextView)itemView.findViewById(R.id.matriculeTextView);
        this.time=(TextView)itemView.findViewById(R.id.dateTextView);
        this.stateimageview=itemView.findViewById(R.id.stateImageView);
         this.comment=itemView.findViewById(R.id.commenttv);
        this.commenttv=itemView.findViewById(R.id.commenttvv);



    }








    public void updateUI(TicketRetrieve ticketRetrieve){
        String titleString= ticketRetrieve.getTitle();
        String descriptionString=ticketRetrieve.getDescription();
        String url=ticketRetrieve.getPhotolink();
        String statestring=ticketRetrieve.getState();
        Long timelong=ticketRetrieve.getTimestamp();
        String commentaire=ticketRetrieve.getComment();





        title.setText(titleString);
        description.setText(descriptionString);


        if(url != null){
            Glide.with(photolink.getContext()).load(url).into(photolink);

        }else if(url == null){
            photolink.setBackground(cxt.getResources().getDrawable(R.drawable.ic_photo_camera));
            Log.v("photolinksource",url);

        }


        if(statestring.equals("en cours")){
               stateimageview.setImageResource(R.drawable.ic_encours);
               state.setText(statestring);

           }
           else if(statestring.equals("cloturé")){
               stateimageview.setImageResource(R.drawable.ic_thumbs_up);
               state.setText(statestring);

           }else if (statestring.equals("envoyé")){
               stateimageview.setImageResource(R.drawable.ic_sent);
               state.setText(statestring);

           }


         String timelongtostring = String.valueOf(timelong);
         int strlength=timelongtostring.length();
        matricule.setText(timelongtostring.substring(4, strlength-1));

        if(commentaire.equals("empty")){
            comment.setVisibility(View.GONE);
            commenttv.setVisibility(View.GONE);
        }
        comment.setText(commentaire);

        CharSequence date = FormatterUtil.getRelativeTimeSpanString(cxt, timelong);

        time.setText(date);





    }
}
