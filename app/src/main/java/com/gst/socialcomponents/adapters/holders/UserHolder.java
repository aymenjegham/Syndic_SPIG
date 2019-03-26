package com.gst.socialcomponents.adapters.holders;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gst.socialcomponents.R;
import com.gst.socialcomponents.main.main.ToolsActivityMod;
import com.gst.socialcomponents.model.Profilefire;
import com.gst.socialcomponents.model.TicketRetrieve;
import com.gst.socialcomponents.utils.FormatterUtil;
import com.gst.socialcomponents.views.FollowButton;

public class UserHolder extends RecyclerView.ViewHolder {



    private Context context;
    private ImageView photoImageView;
    private TextView nameTextView;
    private Switch activationButton;
    private TextView matriculeresidence;
    private TextView residence;
    private TextView mobileno;
    ToolsActivityMod toolsActivityMod=new ToolsActivityMod();


    public UserHolder(View profileCard) {
        super(profileCard);


        this.photoImageView=itemView.findViewById(R.id.photoImageView);
        this.nameTextView =itemView.findViewById(R.id.nameTextView);
        this.activationButton=itemView.findViewById(R.id.activeButton);
        this.matriculeresidence=itemView.findViewById(R.id.matriculeresidence);
        this.residence=itemView.findViewById(R.id.residence);
        this.mobileno=itemView.findViewById(R.id.mobileno);

    }



    public void updateUI(Profilefire profilefire){
        String username= profilefire.getUsername();
        String matricule= profilefire.getNumresidence();
        String residencestring= profilefire.getResidence();
        String nummobile= profilefire.getMobile();
        String urlpath =profilefire.getPhotoUrl();
        Boolean isactivated =profilefire.isActive();
        String  userid =profilefire.getId();

        nameTextView.setText(username);
        matriculeresidence.setText(matricule);
        residence.setText(residencestring);
        mobileno.setText(nummobile);
        Glide.with(photoImageView.getContext()).load(urlpath).into(photoImageView);

        if(isactivated){
            activationButton.setChecked(true);
            activationButton.setText("Activé");
            activationButton.setTextColor(Color.BLUE);
        }else
        {
            activationButton.setChecked(false);
            activationButton.setText("Désactivé");
            activationButton.setTextColor(Color.RED);
        }
        activationButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                     toolsActivityMod.changestate(userid,true,buttonView);
                    activationButton.setChecked(true);
                    activationButton.setText("Activé");
                    activationButton.setTextColor(Color.BLUE);
                 }
                else {
                    toolsActivityMod.changestate(userid,false,buttonView);
                    activationButton.setChecked(false);
                    activationButton.setText("Désactivé");
                    activationButton.setTextColor(Color.RED);

                }
            }
        });






    }
}
