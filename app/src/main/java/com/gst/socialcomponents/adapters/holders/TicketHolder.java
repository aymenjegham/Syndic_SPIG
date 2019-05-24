package com.gst.socialcomponents.adapters.holders;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.firebase.database.collection.LLRBNode;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;

import java.io.IOException;

import static com.gst.socialcomponents.R.drawable.com_facebook_button_background;
import static com.gst.socialcomponents.R.drawable.ic_video;

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
    private VideoView videoview;
     ProgressBar progDailog;







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
        this.videoview=itemView.findViewById(R.id.videoView2);
        this.progDailog=itemView.findViewById(R.id.progressBar2);




    }








    public void updateUI(TicketRetrieve ticketRetrieve,Context cxt){
        String titleString= ticketRetrieve.getTitle();
        String descriptionString=ticketRetrieve.getDescription();
        String url=ticketRetrieve.getPhotolink();
        Integer statestring=ticketRetrieve.getState();
        Long timelong=ticketRetrieve.getTimestamp();
        String commentaire=ticketRetrieve.getComment();
        Integer type=ticketRetrieve.getType();





        title.setText(titleString);
        description.setText(descriptionString);


        if(url != null && type==0){
            progDailog.setVisibility(View.GONE);
            Glide.with(photolink.getContext()).load(url).into(photolink);


        }else if (url != null && type==1){


            videoview.setVisibility(View.VISIBLE);
             videoview.setMediaController(new MediaController(cxt));
            videoview.setVideoURI(Uri.parse("http://syndicspig.gloulougroupe.com/VideoUpload/Upload/"+url));







            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {


                public void onPrepared(MediaPlayer mp) {

                    progDailog.setVisibility(View.GONE);
                    videoview.start();
                }
            });

            videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    videoview.start();

                }
            });



        }else if(url == null) {
            photolink.setBackground(cxt.getResources().getDrawable(R.drawable.ic_photo_camera));
            progDailog.setVisibility(View.GONE);

        }



        if(statestring ==1){
               stateimageview.setImageResource(R.drawable.ic_encours);
               state.setText("en cours");

           }
           else if(statestring == 2){
               stateimageview.setImageResource(R.drawable.ic_thumbs_up);
               state.setText("cloturé");

           }else if (statestring == 0){
               stateimageview.setImageResource(R.drawable.ic_sent);
               state.setText("envoyé");

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
