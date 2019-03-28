package com.gst.socialcomponents.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.Faceholder;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

public class FacesAdapter extends RecyclerView.Adapter<Faceholder> {




    private ArrayList<Profilefire> profiles;
    TicketActivity ticketActivity;
    SwipeController swipeController = null;

    public FacesAdapter(ArrayList<Profilefire> profiles) {
        Log.v("lisreceived",profiles.size()+"  ");

        this.profiles=profiles;
     }

    @Override
    public Faceholder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View profilecard= LayoutInflater.from(parent.getContext()).inflate(R.layout.face_item,parent,false);





        return new Faceholder(profilecard);
    }

    @Override
    public void onBindViewHolder(@NonNull Faceholder faceholder, int i) {
        final Profilefire profilefire =profiles.get(i);
        faceholder.updateUI(profilefire);



    }



    @Override
    public int getItemCount() {



        return profiles.size();
    }
}
