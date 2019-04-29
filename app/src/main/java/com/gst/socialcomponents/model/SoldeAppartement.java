package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class SoldeAppartement {


    @SerializedName("sSoldeActuel")
    int  solde;

    @SerializedName("sRetenu")
    int  sRetenu;

    @SerializedName("sDatefinpaye")
    String date;

    public SoldeAppartement(int solde,String date,int sRetenu) {
        this.solde = solde;
        this.date=date;
        this.sRetenu=sRetenu;
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

    public int getsRetenu() {
        return sRetenu;
    }

    public void setsRetenu(int sRetenu) {
        this.sRetenu = sRetenu;
    }
}
