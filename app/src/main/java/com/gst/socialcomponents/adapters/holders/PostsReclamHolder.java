package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.managers.PostManager;
import com.gst.socialcomponents.model.Post;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;
import com.gst.socialcomponents.utils.GlideApp;

public class PostsReclamHolder extends RecyclerView.ViewHolder {



    private TextView title;
    private TextView description;
    private ImageView photolink;
    private TextView views;
    private TextView likes;
    private TextView time;
    Context cxt;
    private ImageView stateimageview;
    private ImageView photoImageView;
    private TextView userTv;
    private TextView userresidenceTv;
    private  TextView commentlabel;
    private  TextView comments;
    protected PostManager postManager;
    Button ignore,supprime;
    private DatabaseReference reference1,reference2;





    public PostsReclamHolder(View ticketCard) {
        super(ticketCard);


        this.title=(TextView)itemView.findViewById(R.id.titleTextView);
        this.description=(TextView)itemView.findViewById(R.id.detailsTextView );
        this.photolink=(ImageView) itemView.findViewById(R.id.postImageView);
        this.views=(TextView)itemView.findViewById(R.id.watcherCounterTextView);
        this.likes=(TextView)itemView.findViewById(R.id.likeCounterTextView);
        this.time=(TextView)itemView.findViewById(R.id.dateTextView);
        this.stateimageview=itemView.findViewById(R.id.stateImageView);
        this.photoImageView=itemView.findViewById(R.id.photoImageViewticket);
        this.userTv=itemView.findViewById(R.id.usernametv);
        this.userresidenceTv=itemView.findViewById(R.id.residencenumtv);
        this.comments=itemView.findViewById(R.id.commentsCountTextView);
        this.commentlabel=itemView.findViewById(R.id.commenlabelmod);
        this.ignore=itemView.findViewById(R.id.buttonignorer);
        this.supprime=itemView.findViewById(R.id.buttonsupprimer);





    }








    public void updateUI(Post post, Context cxt,String id){
        String titleString= post.getTitle();
        String descriptionString=post.getDescription();
        Long viesString=post.getWatchersCount();
        Long commentscount=post.getCommentsCount();
        Long likescount=post.getLikesCount();
        Long timelong=post.getCreatedDate();



        title.setText(titleString);
        description.setText(descriptionString);
        views.setText(viesString.toString());
        comments.setText(commentscount.toString());
        likes.setText(likescount.toString());

        CharSequence date = FormatterUtil.getRelativeTimeSpanString(cxt, timelong);

        time.setText(date);

         postManager = PostManager.getInstance(cxt.getApplicationContext());
        postManager.loadImageMediumSize(GlideApp.with(cxt), post.getImageTitle(), photolink);


        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference1 = FirebaseDatabase.getInstance().getReference().child("posts").child("hasComplain");

                reference1.setValue(false);

            }
        });

        supprime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cxt, "deleted", Toast.LENGTH_SHORT).show();

            }
        });



    /*    Query query = FirebaseDatabase.getInstance().getReference().child("profiles");
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




*/
    }

}
