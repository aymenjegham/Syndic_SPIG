package com.gst.socialcomponents.adapters.viewPager;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.adapters.holders.UserHolderFacture;
import com.gst.socialcomponents.adapters.holders.UserHolderFactureCheck;
import com.gst.socialcomponents.listeners.SwipeController;
import com.gst.socialcomponents.main.main.TicketActivity;
import com.gst.socialcomponents.main.main.fragments.InvoicingFragment;
import com.gst.socialcomponents.model.Profilefire;

import java.util.ArrayList;

public class UserAdapterFactureCheck extends RecyclerView.Adapter<UserHolderFactureCheck> {


    private ArrayList<Profilefire> profiles;
    TicketActivity ticketActivity;
    SwipeController swipeController = null;
    boolean isSelectedAll;
    SharedPreferences.Editor editor;




    ArrayList<Profilefire> profilestoinvoice = new ArrayList() ;

    public ArrayList<Profilefire> getProfilestoinvoice() {
        return profilestoinvoice;
    }

    public UserAdapterFactureCheck(ArrayList<Profilefire> profiles) {
        this.profiles=profiles;
     }

    public UserAdapterFactureCheck() {


    }

    @Override
    public UserHolderFactureCheck onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View UserCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_list_viewfacture_check,parent,false);
        return new UserHolderFactureCheck(UserCard);
    }




    @Override
    public void onBindViewHolder(@NonNull UserHolderFactureCheck userHolderFacture, int i) {
        final Profilefire profilefire =profiles.get(i);
        userHolderFacture.updateUI(profilefire);

    }



    @Override
    public int getItemCount() {
        return profiles.size();
    }





}
