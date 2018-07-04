package com.example.haniln.test1;

public class item {
    int img;
    String zcode, zname, zmodel, zserial, zmaker, zday, zdpt1, zdpt2, zdpt3, zdpt4;

//    String code;
//    String gear;
//    String name;


    public item(int img, String zcode, String zname, String zmodel, String zserial, String zmaker, String zday, String zdpt1, String zdpt2, String zdpt3, String zdpt4) {
        this.img = img;
        this.zcode = zcode;
        this.zname = zname;
        this.zmodel = zmodel;
        this.zserial = zserial;
        this.zmaker = zmaker;
        this.zday = zday;
        this.zdpt1 = zdpt1;
        this.zdpt2 = zdpt2;
        this.zdpt3 = zdpt3;
        this.zdpt4 = zdpt4;
    }

    public int getImg() {
        return img;
    }

    public String getZcode() {
        return zcode;
    }

    public String getZname() {
        return zname;
    }

    public String getZmodel() {
        return zmodel;
    }

    public String getZserial() {
        return zserial;
    }

    public String getZmaker() {
        return zmaker;
    }

    public String getZday() {
        return zday;
    }

    public String getZdpt1() {
        return zdpt1;
    }

    public String getZdpt2() {
        return zdpt2;
    }

    public String getZdpt3() {
        return zdpt3;
    }

    public String getZdpt4() {
        return zdpt4;
    }
}