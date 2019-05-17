package com.gst.socialcomponents.model;

import com.google.gson.annotations.SerializedName;

public class HistorySyndic {


    @SerializedName("hFraiSyndic")
    Integer frais;

    @SerializedName("hAnnefrais")
    Integer year;



    public HistorySyndic(Integer frais, Integer year) {
        this.frais = frais;
        this.year = year;
    }


    public Integer getFrais() {
        return frais;
    }

    public void setFrais(Integer fraisupposed) {
        this.frais = fraisupposed;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer dateRemiseCle) {
        this.year = dateRemiseCle;
    }


}
