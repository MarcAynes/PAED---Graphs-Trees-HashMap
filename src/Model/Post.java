package Model;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class Post {
    private int id;
    private String [] liked_by;
    private long published_when;
    private String published_by;
    private double [] location; //(latitud,longitud)
    private String[] hashtags;

    public Post(int id, double[] location) {
        this.id = id;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getLiked_by() {
        return liked_by;
    }

    public void setLiked_by(String[] liked_by) {
        this.liked_by = liked_by;
    }

    public long getPublished_when() {
        return published_when;
    }

    public void setPublished_when(long published_when) {
        this.published_when = published_when;
    }

    public String getPublished_by() {
        return published_by;
    }

    public void setPublished_by(String published_by) {
        this.published_by = published_by;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public String[] getHashtags() {
        return hashtags;
    }

    public void setHashtags(String[] hashtags) {
        this.hashtags = hashtags;
    }
}