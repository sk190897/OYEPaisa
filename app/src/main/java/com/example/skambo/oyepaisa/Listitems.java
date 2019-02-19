package com.example.skambo.oyepaisa;

public class Listitems {

    private String mdescription, mamount;

    public Listitems(String mdescription, String mamount) {

        this.mdescription = mdescription;
        this.mamount = mamount;


    }

    public String getMdescription() {
        return mdescription;
    }

    public void setMdescription(String mdescription) {
        this.mdescription = mdescription;
    }

    public String getMamount() {
        return mamount;
    }

    public void setMamount(String mamount) {
        this.mamount = mamount;
    }
}
