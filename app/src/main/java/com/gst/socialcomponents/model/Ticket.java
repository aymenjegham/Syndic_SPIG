package com.gst.socialcomponents.model;


import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DateFormat;
import java.util.Map;

import static java.text.DateFormat.getDateTimeInstance;

@IgnoreExtraProperties
    public class Ticket {

        public String title;
        public String description;
        public Map<String, String> timestamp;
        public String state;
        public String photolink;

        public Ticket() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Ticket(String title, String description,Map<String, String> timestamp,String state,String photolink) {
            this.title = title;
            this.description = description;
            this.timestamp=timestamp;
            this.state=state;
            this.photolink=photolink;
        }





    }

