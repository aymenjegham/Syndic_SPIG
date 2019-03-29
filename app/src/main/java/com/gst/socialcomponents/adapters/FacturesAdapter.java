package com.gst.socialcomponents.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.Faceholder;
import com.gst.socialcomponents.adapters.holders.FactureHolder;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.model.Facture;
import com.gst.socialcomponents.model.Profilefire;

import java.util.ArrayList;

public class FacturesAdapter extends RecyclerView.Adapter<FactureHolder> {




    private ArrayList<Facture> factures;


    public FacturesAdapter(ArrayList<Facture> factures) {
        this.factures=factures;
     }

    @Override
    public FactureHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View facturecard= LayoutInflater.from(parent.getContext()).inflate(R.layout.facturecard,parent,false);
        return new FactureHolder(facturecard);
    }

    @Override
    public void onBindViewHolder(@NonNull FactureHolder factureHolder, int i) {
        final Facture facture =factures.get(i);

       factureHolder.updateUI(facture);

    }



    @Override
    public int getItemCount() {
        return factures.size();
    }
}
