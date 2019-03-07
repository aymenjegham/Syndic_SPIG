package com.gst.socialcomponents.model;


import com.google.firebase.database.IgnoreExtraProperties;

import java.text.DateFormat;
import java.util.Map;

import static java.text.DateFormat.getDateTimeInstance;

@IgnoreExtraProperties
public class TicketRetrieve {

    public String title;
    public String description;
    public Long timestamp;
    public String state;
    public String photolink;

    public TicketRetrieve() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public TicketRetrieve(String title, String description,Long timestamp,String state,String photolink) {
        this.title = title;
        this.description = description;
        this.timestamp=timestamp;
        this.state=state;
        this.photolink=photolink;
    }





}

