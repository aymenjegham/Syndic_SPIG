package com.gst.socialcomponents.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.UserHolder;
import com.gst.socialcomponents.adapters.holders.UserHolderFacture;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.FacturationActivity;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.main.main.ToolsActivityMod;
import com.gst.socialcomponents.main.main.fragments.InvoicingFragment;
import com.gst.socialcomponents.model.Profilefire;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class UserAdapterFacture extends RecyclerView.Adapter<UserHolderFacture> {


    private ArrayList<Profilefire> profiles;
    TicketActivity ticketActivity;
    SwipeController swipeController = null;
    boolean isSelectedAll;
    SharedPreferences.Editor editor;


InvoicingFragment invoicingFragment=new InvoicingFragment();


    ArrayList<Profilefire> profilestoinvoice = new ArrayList() ;

    public ArrayList<Profilefire> getProfilestoinvoice() {
        return profilestoinvoice;
    }

    public UserAdapterFacture(ArrayList<Profilefire> profiles) {
        this.profiles=profiles;
     }

    public UserAdapterFacture() {


    }

    @Override
    public UserHolderFacture onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View UserCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_list_viewfacture,parent,false);





        return new UserHolderFacture(UserCard);
    }

    public void selectAll(){
         isSelectedAll=true;
        notifyDataSetChanged();
    }



    @Override
    public void onBindViewHolder(@NonNull UserHolderFacture userHolderFacture, int i) {
        final Profilefire profilefire =profiles.get(i);
        userHolderFacture.updateUI(profilefire);


        if (!isSelectedAll) userHolderFacture.checkall(false);
        else userHolderFacture.checkall(true);



        userHolderFacture.getFacturecheck().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    profilestoinvoice.add(profilefire);
                    invoicingFragment.gettolist(profilestoinvoice);

                }else {
                     profilestoinvoice.remove(profilefire);
                     invoicingFragment.gettolist(profilestoinvoice);

                }



            }
        });





    }



    @Override
    public int getItemCount() {



        return profiles.size();
    }





}
