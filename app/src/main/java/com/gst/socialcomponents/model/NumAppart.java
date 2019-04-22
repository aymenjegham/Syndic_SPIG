package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class NumAppart {

    @SerializedName("cbmarq")
    String cbmarq;

    public NumAppart(String cbmarq) {
        this.cbmarq = cbmarq;
    }

    public String getCbmarq() {
        return cbmarq;
    }

    public void setCbmarq(String cbmarq) {
        this.cbmarq = cbmarq;
    }
}
