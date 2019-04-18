package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class Appartements {

    @SerializedName("a_intitule")
    String a_intitule;

    public Appartements(String a_intitule) {
        this.a_intitule = a_intitule;
    }

    public String getA_intitule() {
        return a_intitule;
    }

    public void setA_intitule(String a_intitule) {
        this.a_intitule = a_intitule;
    }
}
