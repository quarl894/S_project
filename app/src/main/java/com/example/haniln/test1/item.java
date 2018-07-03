package com.example.haniln.test1;

public class item {
    int img;
    String code;
    String gear;
    String name;

    public int getImg() {
        return img;
    }
    public String getCode() { return code; }
    public String getGear() {
        return gear;
    }
    public String getName() {
        return name;
    }

    public item(int img, String code, String gear, String name) {
        this.img = img;
        this.code = code;
        this.gear = gear;
        this.name = name;
    }
}