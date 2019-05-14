package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class Blocs {

    @SerializedName("b_intitule")
    String b_intitule;

    @SerializedName("cbmarq")
    Integer cbmarq;

    public Blocs(String b_intitule, Integer cbmarq) {
        this.b_intitule = b_intitule;
        this.cbmarq=cbmarq;
    }

    public String getB_intitule() {
        return b_intitule;
    }

    public void setB_intitule(String a_intitule) {
        this.b_intitule = a_intitule;
    }

    public Integer getCbmarq() {
        return cbmarq;
    }

    public void setCbmarq(Integer cbmarq) {
        this.cbmarq = cbmarq;
    }
}
