package com.gst.socialcomponents.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostProfile {

 @SerializedName("active")
    @Expose
    private Boolean active;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("likescount")
    @Expose
    private Integer likescount;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("numresidence")
    @Expose
    private String numresidence;

    @SerializedName("photoUrl")
    @Expose
    private String photoUrl;

    @SerializedName("residence")
    @Expose
    private String residence;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("type")
    @Expose
    private Boolean type;

    @SerializedName("username")
    @Expose
    private String username;


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLikescount() {
        return likescount;
    }

    public void setLikescount(Integer likescount) {
        this.likescount = likescount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNumresidence() {
        return numresidence;
    }

    public void setNumresidence(String numresidence) {
        this.numresidence = numresidence;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public String toString() {
        return "Post{" +
                "active='" + active + '\'' +
                ", email='" + email + '\'' +
                ", id=" + id +
                ", likescount=" + likescount +
                ", mobile=" + mobile +
                ", numresidence=" + numresidence +
                ", photoUrl=" + photoUrl +
                ", residence=" + residence +
                ", token=" + token +
                ", type=" + type +
                ", username=" + username +
                '}';
    }



  /*
  @SerializedName("title")
  @Expose
  private String title;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("id")
    @Expose
    private Integer id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }

    */

}
