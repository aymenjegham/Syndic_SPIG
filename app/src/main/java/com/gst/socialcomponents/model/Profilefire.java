package com.gst.socialcomponents.model;

public class Profilefire {

    private String residence;
    private String numresidence;
    private String mobile;
    private String photoUrl;
    private long likesCount;
    private String registrationToken;
    private String id;
    private String username;
    private String email;
    private boolean active;
    private boolean type;


    public Profilefire(String residence, String numresidence, String mobile, String photoUrl, long likesCount, String registrationToken, String id, String username, String email, boolean active, boolean type) {
        this.residence = residence;
        this.numresidence = numresidence;
        this.mobile = mobile;
        this.photoUrl = photoUrl;
        this.likesCount = likesCount;
        this.id = id;
        this.username = username;
        this.email = email;
        this.active = active;
        this.type = type;
    }

    public Profilefire() {
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getNumresidence() {
        return numresidence;
    }

    public void setNumresidence(String numresidence) {
        this.numresidence = numresidence;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public long getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(long likesCount) {
        this.likesCount = likesCount;
    }

    public String getRegistrationToken() {
        return registrationToken;
    }

    public void setRegistrationToken(String registrationToken) {
        this.registrationToken = registrationToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
