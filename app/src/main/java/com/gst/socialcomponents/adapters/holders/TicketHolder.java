package com.gst.socialcomponents.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.model.TicketRetrieve;

public class TicketHolder extends RecyclerView.ViewHolder {



    private TextView title;

    public TicketHolder(View ticketCard) {
        super(ticketCard);


        this.title=(TextView)itemView.findViewById(R.id.title);

    }








    public void updateUI(TicketRetrieve ticketRetrieve){
        String titleString= ticketRetrieve.getTitle();
        title.setText(titleString);
    }
}
