package com.gst.socialcomponents.model;


import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DepenseListResult {


    private String compteur;
    private long  datefacture;
    private long  createdDate;
    private Integer iddepense;
    private Integer montant;
    private int publier;
    private String residence;
    private String title;
    private String authorId;
    private String imageTitle;
    private String contrat;

    public DepenseListResult(String compteur, long datefacture, Integer iddepense, Integer montant, String residence, String title,String authorId,String imageTitle,String contrat,long createdDate ,int publier) {
        this.compteur = compteur;
        this.datefacture = datefacture;
        this.iddepense = iddepense;
        this.montant = montant;
        this.residence = residence;
        this.title = title;
        this.authorId=authorId;
        this.imageTitle=imageTitle;
        this.contrat=contrat;
        this.createdDate=createdDate;
        this.publier=publier;
      }


    public int getPublier() {
        return publier;
    }

    public void setPublier(int publier) {
        this.publier = publier;
    }

    public long getCreateddate() {
        return createdDate;
    }

    public void setCreateddate(long createddate) {
        this.createdDate = createddate;
    }

    public String getContrat() {
        return contrat;
    }

    public void setContrat(String contrat) {
        this.contrat = contrat;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public DepenseListResult() {
    }


    public String getCompteur() {
        return compteur;
    }

    public void setCompteur(String compteur) {
        this.compteur = compteur;
    }

    public long getDatefacture() {
        return datefacture;
    }

    public void setDatefacture(long datefacture) {
        this.datefacture = datefacture;
    }

    public Integer getIddepense() {
        return iddepense;
    }

    public void setIddepense(Integer iddepense) {
        this.iddepense = iddepense;
    }

    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
