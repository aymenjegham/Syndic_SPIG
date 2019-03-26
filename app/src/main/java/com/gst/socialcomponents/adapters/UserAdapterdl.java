package com.gst.socialcomponents.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.TicketHolder;
import com.gst.socialcomponents.adapters.holders.UserHolder;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

public class UserAdapterdl extends RecyclerView.Adapter<UserHolder> {


    private ArrayList<Profilefire> profiles;
    TicketActivity ticketActivity;
    SwipeController swipeController = null;

    public UserAdapterdl(ArrayList<Profilefire> profiles) {
        this.profiles=profiles;
     }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View UserCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_list_viewsecond,parent,false);





        return new UserHolder(UserCard);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder userHolder, int i) {
        final Profilefire profilefire =profiles.get(i);
        userHolder.updateUI(profilefire);


    }



    @Override
    public int getItemCount() {



        return profiles.size();
    }
}
