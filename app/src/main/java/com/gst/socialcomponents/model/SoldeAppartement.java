package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class SoldeAppartement {


    @SerializedName("sSoldeActuel")
    int  solde;

    public SoldeAppartement(int solde) {
        this.solde = solde;
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }
}
