package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SoldeAppartement {


    @SerializedName("sSoldeActuel")
    int  solde;

    @SerializedName("sDatefinpaye")
    String date;

    public SoldeAppartement(int solde,String date) {
        this.solde = solde;
        this.date=date;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
