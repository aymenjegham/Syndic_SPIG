package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.TicketHolder;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.listeners.SwipeControllerActions;
import com.gst.socialcomponents.main.main.MainActivity;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.Ticket;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketHolder> {


    private ArrayList<TicketRetrieve> tickets;
    TicketActivity ticketActivity;
    SwipeController swipeController = null;

    public TicketAdapter(ArrayList<TicketRetrieve> tickets) {
        this.tickets=tickets;
     }

    @Override
    public TicketHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View ticketCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.ticketcard,parent,false);





        return new TicketHolder(ticketCard);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketHolder ticketHolder, int i) {
        final TicketRetrieve ticketRetrieve =tickets.get(i);
        ticketHolder.updateUI(ticketRetrieve);


    }



    @Override
    public int getItemCount() {



        return tickets.size();
    }
}
