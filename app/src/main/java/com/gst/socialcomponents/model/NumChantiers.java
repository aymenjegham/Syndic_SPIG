package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class NumChantiers {



    @SerializedName("cbmarq")
    int cbmarq;

    public NumChantiers(int cbmarq) {

        this.cbmarq = cbmarq;
    }

    public int getCbmarq() {
        return cbmarq;
    }

    public void setCbmarq(int cbmarq) {
        this.cbmarq = cbmarq;
    }



}
