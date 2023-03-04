package com.example.bottomnavigation.Model;

public class NotificationModel {
    int img;
    String mention , time;

    public NotificationModel() {
    }

    public NotificationModel(int img, String mention, String time) {
        this.img = img;
        this.mention = mention;
        this.time = time;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
