package com.gst.socialcomponents.model;

import java.util.ArrayList;

public class Coming {

    public String usersid;
    public Boolean state;

    public Coming(String usersid, Boolean state) {
        this.usersid = usersid;
        this.state = state;
    }

    public Coming() {
    }

    public String getUsersid() {
        return usersid;
    }

    public void setUsersid(String usersid) {
        this.usersid = usersid;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
