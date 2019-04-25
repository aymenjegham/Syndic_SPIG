package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class InfoSyndic {


    @SerializedName("iFraiSyndic")
    Integer fraisupposed;

    @SerializedName("iDateRemiseCle")
    String dateRemiseCle;

    @SerializedName("iPeriodicity")
    Integer periodicity;

    public InfoSyndic(Integer fraisupposed, String dateRemiseCle,Integer periodicity) {
        this.fraisupposed = fraisupposed;
        this.dateRemiseCle = dateRemiseCle;
    }


    public Integer getFraisupposed() {
        return fraisupposed;
    }

    public void setFraisupposed(Integer fraisupposed) {
        this.fraisupposed = fraisupposed;
    }

    public String getDateRemiseCle() {
        return dateRemiseCle;
    }

    public void setDateRemiseCle(String dateRemiseCle) {
        this.dateRemiseCle = dateRemiseCle;
    }

    public Integer getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Integer periodicity) {
        this.periodicity = periodicity;
    }
}
