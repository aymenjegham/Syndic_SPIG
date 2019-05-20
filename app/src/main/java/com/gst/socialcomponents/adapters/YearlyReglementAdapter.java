package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.FactureHolder;
import com.gst.socialcomponents.adapters.holders.YearHolder;
import com.gst.socialcomponents.model.Facture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class YearlyReglementAdapter extends RecyclerView.Adapter<YearHolder> {




    private ArrayList<Integer> years;
    private String appartId;
    Context cxt;
    String residence;
    private ArrayList<Integer> frais ;


    public YearlyReglementAdapter(ArrayList<Integer> years,String appartId,Context cxt,String residence,int yearactual,ArrayList<Integer> frais) {
        this.years=years;
        this.appartId=appartId;
        this.cxt=cxt;
        this.residence=residence;
        this.frais=frais;
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

        FirebaseFunctions.getInstance().getHttpsCallable("getTime")
                .call().addOnSuccessListener(new OnSuccessListener<HttpsCallableResult>() {
            @Override
            public void onSuccess(HttpsCallableResult httpsCallableResult) {
                Long timestamp = (long) httpsCallableResult.getData();
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
                Date datenow = new Date(timestamp);
                cal.setTime(datenow);
                int yearactual = cal.get(Calendar.YEAR);

                if(yearactual == year){
                    yearHolder.updateUI(yearactual,appart,cxt,residence);

                }else if(yearactual != year){
                    final Integer frai=frais.get(i);
                    yearHolder.updateUI2(year,appart,cxt,residence,frai);


                }
            }
        });




    }



    @Override
    public int getItemCount() {

        return years.size();
    }
}
