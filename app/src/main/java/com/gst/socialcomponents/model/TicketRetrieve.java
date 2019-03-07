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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhotolink() {
        return photolink;
    }

    public void setPhotolink(String photolink) {
        this.photolink = photolink;
    }
}

