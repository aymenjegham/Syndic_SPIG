package com.gst.socialcomponents.model;

import android.widget.ImageView;

public class Factureitemdata {

    String name;
    String montant;
    String remise_cle;
    int imgview;

    public int getImgview() {
        return imgview;
    }

    public void setImgview(int imgview) {
        this.imgview = imgview;
    }

    public Factureitemdata(String name, String montant, String remise_cle,int imageView) {
        this.name = name;
        this.montant = montant;
        this.remise_cle = remise_cle;
        this.imgview=imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getRemise_cle() {
        return remise_cle;
    }

    public void setRemise_cle(String remise_cle) {
        this.remise_cle = remise_cle;
    }
}
