package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.FactureHolder;
import com.gst.socialcomponents.adapters.holders.YearHolder;
import com.gst.socialcomponents.model.Facture;

import java.util.ArrayList;

public class YearlyReglementAdapter extends RecyclerView.Adapter<YearHolder> {




    private ArrayList<Integer> years;
    private String appartId;
    Context cxt;
    String residence;


    public YearlyReglementAdapter(ArrayList<Integer> years,String appartId,Context cxt,String residence) {
        this.years=years;
        this.appartId=appartId;
        this.cxt=cxt;
        this.residence=residence;
     }

    @Override
    public YearHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View yearlist= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_reglement,parent,false);
        return new YearHolder(yearlist);
    }

    @Override
    public void onBindViewHolder(@NonNull YearHolder yearHolder, int i) {
        final Integer year =years.get(i);
        final String appart=appartId;


       yearHolder.updateUI(year,appart,cxt,residence);

    }



    @Override
    public int getItemCount() {

        return years.size();
    }
}
