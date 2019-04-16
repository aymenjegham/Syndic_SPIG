package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.TicketHolderMod;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.main.main.TicketDetailActivity;
import com.gst.socialcomponents.model.TicketRetrieve;

import java.util.ArrayList;

public class TicketAdapterMod extends RecyclerView.Adapter<TicketHolderMod> {


    private ArrayList<TicketRetrieve> tickets;
    ArrayList<String> ticketscreators = new ArrayList() ;
    ArrayList<String> ticketsid = new ArrayList() ;


    TicketActivity ticketActivity;
    SwipeController swipeController = null;
    Context cxt;

    public TicketAdapterMod(ArrayList<TicketRetrieve> tickets,ArrayList<String> ticketscreators,ArrayList<String> ticketsid,Context cxt ) {
        this.tickets=tickets;
        this.ticketscreators=ticketscreators;
        this.cxt=cxt;
        this.ticketsid=ticketsid;
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
        ticketHolderMod.updateUI(ticketRetrieve,userkey,cxt.getApplicationContext());

        ticketHolderMod.itemView.setOnClickListener(new View.OnClickListener() {
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



    }



    @Override
    public int getItemCount() {



        return tickets.size();
    }
}
