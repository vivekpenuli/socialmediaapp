package com.example.bottomnavigation.Model;

public class SignupData {
    String name, profession, email, pass, cover_photo;

    public SignupData() {
    }

    public SignupData(String name, String profession, String email, String pass, String cover_photo) {
        this.name = name;
        this.profession = profession;
        this.email = email;
        this.pass = pass;
        this.cover_photo = cover_photo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }
}