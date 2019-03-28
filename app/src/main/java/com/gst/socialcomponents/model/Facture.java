package com.gst.socialcomponents.model;

import java.util.Map;

public class Facture {
    public String title;
    public String montant;
    public Map<String, String> timestamp;
    public String datede;
    public String datejusqua;
    public String datelimite;




    public Facture(String title, String montant, Map<String, String> timestamp, String datede, String datejusqua, String datelimite) {
        this.title = title;
        this.montant = montant;
        this.timestamp = timestamp;
        this.datede = datede;
        this.datejusqua = datejusqua;
        this.datelimite = datelimite;
    }
}