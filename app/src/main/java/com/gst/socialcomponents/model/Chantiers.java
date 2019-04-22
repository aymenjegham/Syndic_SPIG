package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class Chantiers {

    @SerializedName("C_intitule")
    String C_intitule;

    @SerializedName("cbmarq")
    int cbmarq;

    public Chantiers(String c_intitule, int cbmarq) {
        C_intitule = c_intitule;
        this.cbmarq = cbmarq;
    }

    public int getCbmarq() {
        return cbmarq;
    }

    public void setCbmarq(int cbmarq) {
        this.cbmarq = cbmarq;
    }

    public String getC_intitule() {
        return C_intitule;
    }

    public void setC_intitule(String c_intitule) {
        C_intitule = c_intitule;
    }
}
