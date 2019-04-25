package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.main.fragments.InvoicingFragment;

import java.util.ArrayList;

public class UserHolderAppartment extends RecyclerView.ViewHolder {

    private Context context;

    private TextView appart;
    private ImageView img_view;
    public CheckBox itemappartchbx;

    public UserHolderAppartment(View profileCard) {

        super(profileCard);
        this.appart=itemView.findViewById(R.id.appart);
        this.img_view=itemView.findViewById(R.id.icontypehouse);
        this.itemappartchbx=itemView.findViewById(R.id.checkBoxitemappart);

    }
    public Integer updateUI(String appartstr, Integer numresidence, ToggleButton ajoutall, Button facture){
        String  appartment =appartstr;
        appart.setText(appartment);
        img_view.setImageResource(R.drawable.ic_home_black_24dp);


        return numresidence;
    }

    public void checkall(boolean value){

        itemappartchbx.setChecked(value);


    }
}
