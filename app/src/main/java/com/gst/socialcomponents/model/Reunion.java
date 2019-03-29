package com.gst.socialcomponents.model;


import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Map;

@IgnoreExtraProperties
    public class Reunion {

        public ArrayList<Coming> userids=new ArrayList<>();
        public String titre;
        public Map<String, String> timestamp;
        public String sujet;
        public String date;
        public String heure;
        public String location;

        public Reunion() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }



    public Reunion(ArrayList<Coming> userids, String titre, Map<String, String> timestamp, String sujet, String date, String heure, String location) {
        this.userids = userids;
        this.titre = titre;
        this.timestamp = timestamp;
        this.sujet = sujet;
        this.date = date;
        this.heure = heure;
        this.location = location;
    }
}

