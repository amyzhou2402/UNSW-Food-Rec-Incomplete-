package au.edu.unsw.infs3634.opennow;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Venue {
    @PrimaryKey
    private int id;
    private String name;
    private String location;
    private String url;
    private int opens;
    private int closes;

    public Venue(int id, String name, String location, String url, int opens, int closes) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.url = url;
        this.opens = opens;
        this.closes = closes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOpens() {
        return opens;
    }

    public void setOpens(int opens) {
        this.opens = opens;
    }

    public int getCloses() {
        return closes;
    }

    public void setCloses(int closes) {
        this.closes = closes;
    }
}
