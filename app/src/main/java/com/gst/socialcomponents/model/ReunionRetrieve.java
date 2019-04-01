package com.gst.socialcomponents.model;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Map;

@IgnoreExtraProperties
    public class ReunionRetrieve {

        public ArrayList<Coming> userids=new ArrayList<>();
        public String titre;
        public Long timestamp;
        public String sujet;
        public String date;
        public String heure;
        public String location;

        public ReunionRetrieve() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }



    public ReunionRetrieve(ArrayList<Coming> userids, String titre, Long timestamp, String sujet, String date, String heure, String location) {
        this.userids = userids;
        this.titre = titre;
        this.timestamp = timestamp;
        this.sujet = sujet;
        this.date = date;
        this.heure = heure;
        this.location = location;
    }


    public ArrayList<Coming> getUserids() {
        return userids;
    }

    public void setUserids(ArrayList<Coming> userids) {
        this.userids = userids;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

