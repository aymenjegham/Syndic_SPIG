package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.main.ToolsActivityMod;
import com.gst.socialcomponents.main.main.fragments.ActivationFragment;
import com.gst.socialcomponents.main.main.fragments.InvoicingFragment;
import com.gst.socialcomponents.model.Profilefire;

public class UserHolderFactureCheck extends RecyclerView.ViewHolder {



    private Context context;
    private ImageView photoImageView;
    private TextView nameTextView;

    public CheckBox getFacturecheck() {
        return facturecheck;
    }

    private CheckBox facturecheck;
    private TextView matriculeresidence;
    private TextView residence;
    private TextView mobileno;
    ToolsActivityMod toolsActivityMod=new ToolsActivityMod();
    ActivationFragment activationFragment =new ActivationFragment();
    ConstraintLayout constraintLayout;
    InvoicingFragment invoicingFragment;




    public int counter=0;

    public int getCounter() {
        return counter;
    }

    public UserHolderFactureCheck(View profileCard) {
        super(profileCard);


        this.photoImageView=itemView.findViewById(R.id.photoImageView);
        this.nameTextView =itemView.findViewById(R.id.nameTextView);
        this.facturecheck=itemView.findViewById(R.id.facture_checkbox);
        this.matriculeresidence=itemView.findViewById(R.id.matriculeresidence);
        this.residence=itemView.findViewById(R.id.residence);
        this.mobileno=itemView.findViewById(R.id.mobileno);
        this.constraintLayout=itemView.findViewById(R.id.itemlayout);

    }








    public void updateUI(Profilefire profilefire){
        String username= profilefire.getUsername();
        String matricule= profilefire.getNumresidence();
        String residencestring= profilefire.getResidence();
        String nummobile= profilefire.getMobile();
        String urlpath =profilefire.getPhotoUrl();





        nameTextView.setText(username);
        matriculeresidence.setText(matricule);
        residence.setText(residencestring);
        mobileno.setText(nummobile);
        Glide.with(photoImageView.getContext()).load(urlpath).into(photoImageView);


        invoicingFragment=new InvoicingFragment();







    }
}
