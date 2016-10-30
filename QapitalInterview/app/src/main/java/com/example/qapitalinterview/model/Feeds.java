package com.example.qapitalinterview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Feeds {

    @SerializedName("feed")
    @Expose
    private List<Feed> feed = new ArrayList<Feed>();

    /**
     *
     * @return
     * The feed
     */
    public List<Feed> getFeed() {
        return feed;
    }

    /**
     *
     * @param feed
     * The feed
     */
    public void setFeed(List<Feed> feed) {
        this.feed = feed;
    }

}
