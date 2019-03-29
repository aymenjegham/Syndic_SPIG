package com.gst.socialcomponents.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public class Facture {
    public String title;
    public String montant;
    public Long timestamp;
    public Map<String, String> timestampupdate;
    public String datede;
    public String datejusqua;
    public String datelimite;
    public Boolean statut;


    public Facture() {
    }

    public Facture(String title, String montant, Long timestamp, String datede, String datejusqua, String datelimite, Boolean statut) {
        this.title = title;
        this.montant = montant;
        this.timestamp = timestamp;
        this.datede = datede;
        this.datejusqua = datejusqua;
        this.datelimite = datelimite;
        this.statut=statut;
    }

    public Facture(String title, String montant, Map<String, String> timestampupdate, String datede, String datejusqua, String datelimite, Boolean statut) {
        this.title = title;
        this.montant = montant;
        this.timestampupdate = timestampupdate;
        this.datede = datede;
        this.datejusqua = datejusqua;
        this.datelimite = datelimite;
        this.statut=statut;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDatede() {
        return datede;
    }

    public void setDatede(String datede) {
        this.datede = datede;
    }

    public String getDatejusqua() {
        return datejusqua;
    }

    public void setDatejusqua(String datejusqua) {
        this.datejusqua = datejusqua;
    }

    public String getDatelimite() {
        return datelimite;
    }

    public void setDatelimite(String datelimite) {
        this.datelimite = datelimite;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }
}