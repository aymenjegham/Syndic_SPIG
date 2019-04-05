package com.gst.socialcomponents.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.TicketHolder;
import com.gst.socialcomponents.adapters.holders.TicketHolderMod;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

public class TicketAdapterMod extends RecyclerView.Adapter<TicketHolderMod> {


    private ArrayList<TicketRetrieve> tickets;
    ArrayList<String> ticketscreators = new ArrayList() ;

    TicketActivity ticketActivity;
    SwipeController swipeController = null;

    public TicketAdapterMod(ArrayList<TicketRetrieve> tickets,ArrayList<String> ticketscreators ) {
        this.tickets=tickets;
        this.ticketscreators=ticketscreators;
     }

    @Override
    public TicketHolderMod onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View ticketCardmod= LayoutInflater.from(parent.getContext()).inflate(R.layout.ticketcardmod,parent,false);





        return new TicketHolderMod(ticketCardmod);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolderMod ticketHolderMod, int i) {
        final TicketRetrieve ticketRetrieve =tickets.get(i);
        final String userkey =ticketscreators.get(i);
        ticketHolderMod.updateUI(ticketRetrieve,userkey);



    }



    @Override
    public int getItemCount() {



        return tickets.size();
    }
}
