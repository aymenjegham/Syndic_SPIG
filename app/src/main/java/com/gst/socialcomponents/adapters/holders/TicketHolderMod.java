package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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





    }








    public void updateUI(TicketRetrieve ticketRetrieve,String userkey,Context cxt){
        String titleString= ticketRetrieve.getTitle();
        String descriptionString=ticketRetrieve.getDescription();
        String url=ticketRetrieve.getPhotolink();
        String statestring=ticketRetrieve.getState();
        Long timelong=ticketRetrieve.getTimestamp();
        String commentaire=ticketRetrieve.getComment();

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
        Glide.with(photolink.getContext()).load(url).into(photolink);


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
