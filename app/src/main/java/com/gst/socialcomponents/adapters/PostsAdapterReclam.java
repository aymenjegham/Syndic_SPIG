package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.PostsReclamHolder;
import com.gst.socialcomponents.adapters.holders.TicketHolderMod;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.main.main.TicketDetailActivity;
import com.gst.socialcomponents.model.Post;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

public class PostsAdapterReclam extends RecyclerView.Adapter<PostsReclamHolder> {


    private ArrayList<Post> posts;
    private ArrayList<String> ids;




    Context cxt;

    public PostsAdapterReclam(ArrayList<Post> posts, Context cxt,ArrayList<String> ids){
        this.posts=posts;
         this.cxt=cxt;
         this.ids=ids;
      }

    @Override
    public PostsReclamHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View ticketCardmod= LayoutInflater.from(parent.getContext()).inflate(R.layout.postreclamcard,parent,false);





        return new PostsReclamHolder(ticketCardmod);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsReclamHolder postsReclamHolder, int i) {
        final Post post = posts.get(i);
        final String id=ids.get(i);
         postsReclamHolder.updateUI(post,cxt.getApplicationContext(),id);

       /* ticketHolderMod.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TicketRetrieve ticketRetrieve =tickets.get(i);
                final String userkey =ticketscreators.get(i);
                final String ticketkey =ticketsid.get(i);

                Intent intent =new Intent(cxt.getApplicationContext(), TicketDetailActivity.class);
                intent.putExtra("ImageUrl",ticketRetrieve.getPhotolink() );
                intent.putExtra("UserKey",userkey );
                intent.putExtra("TicketKey",ticketkey );



                cxt.startActivity(intent);
             }
        });

*/

    }



    @Override
    public int getItemCount() {



        return posts.size();
    }
}
