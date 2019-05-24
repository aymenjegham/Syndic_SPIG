package com.gst.socialcomponents.model;

public class DateFacture {


    String date;
    String timezone;
    Integer timezone_type;

    public DateFacture(String date, String timezone, Integer timezone_type) {
        this.date = date;
        this.timezone = timezone;
        this.timezone_type = timezone_type;
    }

    public DateFacture() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getTimezone_type() {
        return timezone_type;
    }

    public void setTimezone_type(Integer timezone_type) {
        this.timezone_type = timezone_type;
    }
}
