package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.UserHolderAppartment;
import com.gst.socialcomponents.adapters.holders.UserHolderFacture;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.FacturationActivity;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.main.main.fragments.InvoicingFragment;
import com.gst.socialcomponents.model.Profilefire;

import java.util.ArrayList;

public class Adapterappartments extends RecyclerView.Adapter<UserHolderAppartment> {







     ArrayList<String> appartments;
     ArrayList<Integer> numreside;
     ToggleButton ajoutall;
     Button facture;
     ArrayList<Integer> numeroappartments=new ArrayList<>();
     boolean state;
     Context cxt;


    public Adapterappartments(ArrayList<String> apparts, ArrayList<Integer> numreside, ToggleButton ajoutall, Button facture, Boolean state, Context cxt) {
        this.appartments=apparts;
        this.numreside=numreside;
        this.ajoutall=ajoutall;
        this.facture=facture;
        this.state=state;
        numeroappartments=new ArrayList<>();
        this.cxt=cxt;

    }

    @Override
    public UserHolderAppartment onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View UserCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.appartment_item,parent,false);


        return new UserHolderAppartment(UserCard);
    }





    @Override
    public void onBindViewHolder(@NonNull UserHolderAppartment userHolderAppartment, int i) {

        userHolderAppartment.itemappartchbx.setChecked(state);

        if(state){
            numeroappartments.add((numreside.get(i)));
        }

        final String appart =appartments.get(i);
        final Integer numresidence =numreside.get(i);
        userHolderAppartment.updateUI(appart,numresidence,ajoutall,facture);

       userHolderAppartment.itemappartchbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    numeroappartments.add(numreside.get(i));
                }else{
                    numeroappartments.remove(numreside.get(i));
                }

                if(numeroappartments.size()>0){
                    facture.setEnabled(true);
                    facture.setAlpha(1f);
                }else {
                    facture.setEnabled(false);
                    facture.setAlpha(.5f);
                }
            }
        });



        facture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(cxt, FacturationActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(numeroappartments);
                intent.putExtra("mylist",json);
                cxt.startActivity(intent);

            }
        });

     }



    @Override
    public int getItemCount() {
        return appartments.size();
    }





}
