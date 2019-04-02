package com.gst.socialcomponents.model;

import java.util.ArrayList;

public class Coming {

    public String usersid;
    public Boolean state;
    public Boolean seen;

    public Coming(String usersid, Boolean state,Boolean seen) {
        this.usersid = usersid;
        this.state = state;
        this.seen =seen;
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

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}
