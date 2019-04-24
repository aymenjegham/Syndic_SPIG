package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class Appartements {

    @SerializedName("a_intitule")
    String a_intitule;

    @SerializedName("cbmarq")
    Integer cbmarq;

    public Appartements(String a_intitule,Integer cbmarq) {
        this.a_intitule = a_intitule;
        this.cbmarq=cbmarq;
    }

    public String getA_intitule() {
        return a_intitule;
    }

    public void setA_intitule(String a_intitule) {
        this.a_intitule = a_intitule;
    }

    public Integer getCbmarq() {
        return cbmarq;
    }

    public void setCbmarq(Integer cbmarq) {
        this.cbmarq = cbmarq;
    }
}
