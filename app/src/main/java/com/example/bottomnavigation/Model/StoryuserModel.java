package com.example.bottomnavigation.Model;

import java.util.ArrayList;

public class StoryuserModel {

    String storyBy;
    long storyAt;
    ArrayList<UserStories> stories;

    public StoryuserModel() {
    }

    public String getStoryBy() {
        return storyBy;
    }

    public void setStoryBy(String storyBy) {
        this.storyBy = storyBy;
    }

    public long getStoryAt() {
        return storyAt;
    }

    public void setStoryAt(long storyAt) {
        this.storyAt = storyAt;
    }

    public ArrayList<UserStories> getStories() {
        return stories;
    }

    public void setStories(ArrayList<UserStories> stories) {
        this.stories = stories;
    }
    /*
    String name;
int story , profile;

    public StoryuserModel() {
    }

    public StoryuserModel(String name, int story, int profile) {
        this.name = name;
        this.story = story;
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStory() {
        return story;
    }

    public void setStory(int story) {
        this.story = story;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    } */
}
