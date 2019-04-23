package com.gst.socialcomponents.model;

import android.widget.ImageView;

public class Factureitemdata {

    String name;
    String montant;
    String remise_cle;
    int imgview;
    int imgview2;



    public int getImgview2() {
        return imgview2;
    }

    public void setImgview2(int imgview2) {
        this.imgview2 = imgview2;
    }

    public int getImgview() {
        return imgview;
    }

    public void setImgview(int imgview) {
        this.imgview = imgview;
    }

    public Factureitemdata(String name, String montant, String remise_cle,int imageView,int imageview2) {
        this.name = name;
        this.montant = montant;
        this.remise_cle = remise_cle;
        this.imgview=imageView;
        this.imgview2=imageview2;
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
