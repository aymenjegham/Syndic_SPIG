package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class AcceptInfo {


    @SerializedName("reunion_id")
    Integer reunion_id;

    @SerializedName("profile_id")
    Integer profile_id;


    public AcceptInfo(Integer reunion_id, Integer profile_id) {
        this.reunion_id = reunion_id;
        this.profile_id = profile_id;
    }

    public Integer getReunion_id() {
        return reunion_id;
    }

    public void setReunion_id(Integer reunion_id) {
        this.reunion_id = reunion_id;
    }

    public Integer getProfile_id() {
        return profile_id;
    }

    public void setProfile_id(Integer profile_id) {
        this.profile_id = profile_id;
    }
}
