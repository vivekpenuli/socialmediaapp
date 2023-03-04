package com.example.bottomnavigation.Model;

public class PostModel {
   // enh variable ka name or database wale variable ka name same hona chahye

    String postid;
    String postimg;
    String postby;
    long posttime;
    String postdesc;
int commentcount;
    int postlikes;

    public int getPostlikes() {
        return postlikes;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public void setPostlikes(int postlikes) {
        this.postlikes = postlikes;
    }

    public PostModel(String postid, String postimg, String postby, long posttime, String postdesc) {
        this.postid = postid;
        this.postimg = postimg;
        this.postby = postby;
        this.posttime = posttime;
        this.postdesc = postdesc;
    }

    public PostModel() {

    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimg() {
        return postimg;
    }

    public void setPostimg(String postimg) {
        this.postimg = postimg;
    }

    public String getPostby() {
        return postby;
    }

    public void setPostby(String postby) {
        this.postby = postby;
    }

    public long getPosttime() {
        return posttime;
    }

    public void setPosttime(long posttime) {
        this.posttime = posttime;
    }

    public String getPostdesc() {
        return postdesc;
    }

    public void setPostdesc(String postdesc) {
        this.postdesc = postdesc;
    }


}

