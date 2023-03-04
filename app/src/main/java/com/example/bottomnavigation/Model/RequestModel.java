package com.example.bottomnavigation.Model;

public class RequestModel {
//            User user = snapshot.getValue(User.class);  if your are fetching data like this make sure model variable name are same as of database
String name, profession,profile_photo;
String UserID;
int followcount;


    public int getFollowcount() {
        return followcount;
    }

    public void setFollowcount(int followcount) {
        this.followcount = followcount;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public RequestModel() {
    }





    public RequestModel(String name, String profession, String profile_photo) {
        this.name = name;
        this.profession = profession;
        this.profile_photo = profile_photo;
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

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }
}
