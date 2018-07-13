package com.satti.fs.android.nbv.adapter;

import com.satti.fs.android.nbv.network.entities.Location;

import java.io.Serializable;

/**
 * Created by satish on 11/07/18.
 */

public class AdapterModel implements Serializable{

    private String venueName;
    private String categoryIconUrl;
    private String categoryBgIconUrl;
    private Location location;
    //Distance is measured in meters
    //sort by distance

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getCategoryIconUrl() {
        return categoryIconUrl;
    }

    public void setCategoryIconUrl(String categoryIconUrl) {
        this.categoryIconUrl = categoryIconUrl;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCategoryBgIconUrl() {
        return categoryBgIconUrl;
    }

    public void setCategoryBgIconUrl(String categoryBgIconUrl) {
        this.categoryBgIconUrl = categoryBgIconUrl;
    }

    @Override
    public String toString() {
        return "AdapterModel{" +
                "venueName='" + venueName + '\'' +
                ", categoryIconUrl='" + categoryIconUrl + '\'' +
                ", location=" + location +
                ", categoryBgIconUrl='" + categoryBgIconUrl + '\'' +
                '}';
    }

}
