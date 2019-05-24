package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;

public class TicketHolderMod extends RecyclerView.ViewHolder {



    private TextView title;
    private TextView description;
    private ImageView photolink;
    private TextView state;
    private TextView matricule;
    private TextView time;
    Context cxt;
    private ImageView stateimageview;
    private ImageView photoImageView;
    private TextView userTv;
    private TextView userresidenceTv;
    private  TextView commentlabel;
    private  TextView comment;
    private VideoView videoview ;
    private ProgressBar progressbar;




    public TicketHolderMod(View ticketCard) {
        super(ticketCard);


        this.title=(TextView)itemView.findViewById(R.id.title);
        this.description=(TextView)itemView.findViewById(R.id.description );
        this.photolink=(ImageView) itemView.findViewById(R.id.photo);
        this.state=(TextView)itemView.findViewById(R.id.stateTextView);
        this.matricule=(TextView)itemView.findViewById(R.id.matriculeTextView);
        this.time=(TextView)itemView.findViewById(R.id.dateTextView);
        this.stateimageview=itemView.findViewById(R.id.stateImageView);
        this.photoImageView=itemView.findViewById(R.id.photoImageViewticket);
        this.userTv=itemView.findViewById(R.id.usernametv);
        this.userresidenceTv=itemView.findViewById(R.id.residencenumtv);
        this.comment=itemView.findViewById(R.id.commenttv);
        this.commentlabel=itemView.findViewById(R.id.commenlabelmod);
       this.videoview=itemView.findViewById(R.id.videoView3);
       this.progressbar=itemView.findViewById(R.id.progressBar4);





    }








    public void updateUI(TicketRetrieve ticketRetrieve,String userkey,Context cxt){
        String titleString= ticketRetrieve.getTitle();
        String descriptionString=ticketRetrieve.getDescription();
        String url=ticketRetrieve.getPhotolink();
        Integer statestring=ticketRetrieve.getState();
        Long timelong=ticketRetrieve.getTimestamp();
        String commentaire=ticketRetrieve.getComment();
        Integer isvideo =ticketRetrieve.getType();

        Query query = FirebaseDatabase.getInstance().getReference().child("profiles");
        query.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                           if(snapshot.getKey().equals(userkey)){
                               Profilefire profile = snapshot.getValue(Profilefire.class);
                               Glide.with(cxt).load(profile.getPhotoUrl()).into(photoImageView);
                               userTv.setText(profile.getUsername());
                               userresidenceTv.setText(profile.getNumresidence());

                           }

                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });



        title.setText(titleString);
        description.setText(descriptionString);
        photolink.setVisibility(View.VISIBLE);
        Glide.with(photolink.getContext()).load(url).into(photolink);




        if(isvideo == 1){
             progressbar.setVisibility(View.VISIBLE);
            videoview.setVisibility(View.VISIBLE);
          //
            videoview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoview.setMediaController(new MediaController(cxt));
                }
            });
            videoview.setVideoURI(Uri.parse("http://syndicspig.gloulougroupe.com/VideoUpload/Upload/"+url));
            videoview.requestFocus();
            videoview.start();


            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {
                    progressbar.setVisibility(View.GONE);
                    videoview.start();
                }
            });

            videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    videoview.start();
                }
            });

        }




        if(statestring == 1){
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

        if(commentaire.equals("empty")){
            comment.setVisibility(View.GONE);
            commentlabel.setVisibility(View.GONE);
        }else{
            comment.setText(commentaire);
        }


         String timelongtostring = String.valueOf(timelong);
         int strlength=timelongtostring.length();
        matricule.setText(timelongtostring.substring(4, strlength-1));

        CharSequence date = FormatterUtil.getRelativeTimeSpanString(cxt, timelong);

        time.setText(date);





    }
}
