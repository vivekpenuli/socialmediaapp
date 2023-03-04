package com.example.bottomnavigation.Model;

public class User {
    String name , email,pass,profession,cover_photo,profile_photo;

    public User() {
    }

    public User(String name, String email, String pass, String profession) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.profession = profession;
    }  // cover photo ko constructor pe esh leye nhai leya q ke yr user login te time ke value store karegha
    // or login ke time user coverphoto ujpload nhai kar raha to coverphoto ko parameter me pass nhia kar na

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }
}
