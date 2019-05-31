package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;


public class AdsInfo {

    @SerializedName("bp_datefin")
    String bp_datefin;

    @SerializedName("bp_datedebut")
    String bp_datedebut;

    @SerializedName("bp_image")
    String bp_image;

    public AdsInfo(String bp_datefin, String bp_datedebut, String bp_image) {
        this.bp_datefin = bp_datefin;
        this.bp_datedebut = bp_datedebut;
        this.bp_image = bp_image;
    }

    public String getBp_datefin() {
        return bp_datefin;
    }

    public void setBp_datefin(String bp_datefin) {
        this.bp_datefin = bp_datefin;
    }

    public String getBp_datedebut() {
        return bp_datedebut;
    }

    public void setBp_datedebut(String bp_datedebut) {
        this.bp_datedebut = bp_datedebut;
    }

    public String getBp_image() {
        return bp_image;
    }

    public void setBp_image(String bp_image) {
        this.bp_image = bp_image;
    }
}