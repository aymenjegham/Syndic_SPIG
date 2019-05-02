package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.content.Intent;
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
import com.gst.socialcomponents.adapters.PostsAdapterReclam;
import com.gst.socialcomponents.main.main.CalendarActivityMod;
import com.gst.socialcomponents.main.main.MainActivity;
import com.gst.socialcomponents.main.main.ToolsActivityMod;
import com.gst.socialcomponents.main.main.fragments.ReunionslistFragment;
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
                reference1 = FirebaseDatabase.getInstance().getReference().child("posts").child(id).child("hasComplain");
                reference1.setValue(false);
                Toast.makeText(cxt, "Publication instaurée", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(cxt.getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                cxt.getApplicationContext().startActivity(intent);

            }
        });

        supprime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference1 = FirebaseDatabase.getInstance().getReference().child("posts").child(id);
                reference1.removeValue();
                Toast.makeText(cxt, "Publication retirée", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(cxt.getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                cxt.getApplicationContext().startActivity(intent);
            }
        });



    }

}
