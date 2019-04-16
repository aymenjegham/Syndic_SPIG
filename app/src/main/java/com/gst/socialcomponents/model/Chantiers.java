package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class Chantiers {

    @SerializedName("C_intitule")
    String C_intitule;

    public Chantiers(String c_intitule) {
        C_intitule = c_intitule;
    }

    public String getC_intitule() {
        return C_intitule;
    }

    public void setC_intitule(String c_intitule) {
        C_intitule = c_intitule;
    }
}
