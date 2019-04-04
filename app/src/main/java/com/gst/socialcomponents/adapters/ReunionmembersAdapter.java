package com.gst.socialcomponents.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.ReunionistsHolder;
import com.gst.socialcomponents.adapters.holders.TicketHolder;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.Coming;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

public class ReunionmembersAdapter extends RecyclerView.Adapter<ReunionistsHolder> {


    private ArrayList<Coming> reunionists;
    DatabaseReference reference ;
    private FirebaseUser firebaseUser;


    public ReunionmembersAdapter(ArrayList<Coming> reunionists) {
        this.reunionists=reunionists;
     }

    @Override
    public ReunionistsHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View memberCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_reunion,parent,false);





        return new ReunionistsHolder(memberCard);
    }

    @Override
    public void onBindViewHolder(@NonNull ReunionistsHolder reunionistsHolder, int i) {
        final Coming coming =reunionists.get(i);


        String id= coming.getUsersid();
        reference= FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("profiles").child(id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Profilefire profile = dataSnapshot.getValue(Profilefire.class);


                reunionistsHolder.updateUI(profile,coming);}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }



    @Override
    public int getItemCount() {



        return reunionists.size();
    }
}
