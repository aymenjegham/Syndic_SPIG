package com.gst.socialcomponents.model;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Map;

@IgnoreExtraProperties
    public class DataReunion {

        public Integer cbmarq;
        public  Integer cbcreateur;
        public String r_objet;
        public String r_emplacement;
        public String r_datedebut;
        public String r_datefin;
        public String r_description;
        public String r_statut;
        public String cbcreation;

    public DataReunion(Integer cbmarq, Integer cbcreateur, String r_objet, String r_emplacement, String r_datedebut, String r_datefin, String r_description, String r_statut,String cbcreation) {
        this.cbmarq = cbmarq;
        this.cbcreateur = cbcreateur;
        this.r_objet = r_objet;
        this.r_emplacement = r_emplacement;
        this.r_datedebut = r_datedebut;
        this.r_datefin = r_datefin;
        this.r_description = r_description;
        this.r_statut = r_statut;
        this.cbcreation=cbcreation;
    }


    public Integer getCbmarq() {
        return cbmarq;
    }

    public void setCbmarq(Integer cbmarq) {
        this.cbmarq = cbmarq;
    }

    public Integer getCbcreateur() {
        return cbcreateur;
    }

    public void setCbcreateur(Integer cbcreateur) {
        this.cbcreateur = cbcreateur;
    }

    public String getR_objet() {
        return r_objet;
    }

    public void setR_objet(String r_objet) {
        this.r_objet = r_objet;
    }

    public String getR_emplacement() {
        return r_emplacement;
    }

    public void setR_emplacement(String r_emplacement) {
        this.r_emplacement = r_emplacement;
    }

    public String getR_datedebut() {
        return r_datedebut;
    }

    public void setR_datedebut(String r_datedebut) {
        this.r_datedebut = r_datedebut;
    }

    public String getR_datefin() {
        return r_datefin;
    }

    public void setR_datefin(String r_datefin) {
        this.r_datefin = r_datefin;
    }

    public String getR_description() {
        return r_description;
    }

    public void setR_description(String r_description) {
        this.r_description = r_description;
    }

    public String getR_statut() {
        return r_statut;
    }

    public void setR_statut(String r_statut) {
        this.r_statut = r_statut;
    }


    public String getCbcreation() {
        return cbcreation;
    }

    public void setCbcreation(String cbcreation) {
        this.cbcreation = cbcreation;
    }
}

