package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;


public class NumPrincipal {

    @SerializedName("p_principal")
    Integer cbmarq;

    public NumPrincipal(Integer cbmarq) {
        this.cbmarq = cbmarq;
    }

    public Integer getCbmarq() {
        return cbmarq;
    }

    public void setCbmarq(Integer cbmarq) {
        this.cbmarq = cbmarq;
    }
}