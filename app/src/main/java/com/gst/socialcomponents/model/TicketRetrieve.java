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
    public Integer state;
    public String photolink;
    public String comment;

    public TicketRetrieve() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public TicketRetrieve(String title, String description,Long timestamp,Integer state,String photolink,String comment) {
        this.title = title;
        this.description = description;
        this.timestamp=timestamp;
        this.state=state;
        this.photolink=photolink;
        this.comment=comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getPhotolink() {
        return photolink;
    }

    public void setPhotolink(String photolink) {
        this.photolink = photolink;
    }
}

