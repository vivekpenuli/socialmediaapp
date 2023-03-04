package com.example.bottomnavigation.Model;

public class FollowModel {
  String followedby;
  long followtime;

    public FollowModel() {
    }

    public String getFollowedby() {
        return followedby;
    }

    public void setFollowedby(String followedby) {
        this.followedby = followedby;
    }

    public long getFollowtime() {
        return followtime;
    }

    public void setFollowtime(long followtime) {
        this.followtime = followtime;
    }
}
