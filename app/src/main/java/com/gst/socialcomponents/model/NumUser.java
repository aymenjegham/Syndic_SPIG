package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;


public class NumUser {

    @SerializedName("cbmarq")
    Integer cbmarq;

    public NumUser(Integer cbmarq) {
        this.cbmarq = cbmarq;
    }

    public Integer getCbmarq() {
        return cbmarq;
    }

    public void setCbmarq(Integer cbmarq) {
        this.cbmarq = cbmarq;
    }
}