package com.gst.socialcomponents.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.TicketHolder;
import com.gst.socialcomponents.model.Ticket;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketHolder> {


    private ArrayList<TicketRetrieve> tickets;

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

        ticketHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // MainActivity.getMainActivity().LoadDeatilsScreen(station);

            }
        });

    }



    @Override
    public int getItemCount() {



        return tickets.size();
    }
}
