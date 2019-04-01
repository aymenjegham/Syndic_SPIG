package com.gst.socialcomponents.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.ReunionHolder;
import com.gst.socialcomponents.adapters.holders.TicketHolder;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.ReunionRetrieve;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

public class ReunionsAdapter extends RecyclerView.Adapter<ReunionHolder> {


    private ArrayList<ReunionRetrieve> reunions;

    public ReunionsAdapter(ArrayList<ReunionRetrieve> reunions) {
        this.reunions=reunions;
     }

    @Override
    public ReunionHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View reunionCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.reuniontcard,parent,false);





        return new ReunionHolder(reunionCard);
    }

    @Override
    public void onBindViewHolder(@NonNull ReunionHolder reunionHolder, int i) {
        final ReunionRetrieve reunionRetrieve =reunions.get(i);
        reunionHolder.updateUI(reunionRetrieve);


    }



    @Override
    public int getItemCount() {



        return reunions.size();
    }
}
