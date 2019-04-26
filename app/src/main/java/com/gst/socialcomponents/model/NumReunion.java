package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;


public class NumReunion {

    @SerializedName("reunion_id")
    Integer reunionid;

    public NumReunion(Integer reunionid) {
        this.reunionid = reunionid;
    }

    public Integer getReunionid() {
        return reunionid;
    }

    public void setReunionid(Integer cbmarq) {
        this.reunionid = reunionid;
    }
}