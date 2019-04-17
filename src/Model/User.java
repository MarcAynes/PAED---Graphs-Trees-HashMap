package Model;

import java.util.List;

public class User {
    //Atributs de la classe User
    private String username;
    private long creation;
    private String [] to_follow;


    public User(String username, long creation) {
        this.username = username;
        this.creation = creation;
    }

    public User(String username, long creation, String [] to_follow) {
        this.username = username;
        this.creation = creation;
        this.to_follow = to_follow;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getCreation() {
        return creation;
    }

    public void setCreation(long creation) {
        this.creation = creation;
    }

    public String[] getTo_follow() {
        return to_follow;
    }

    public void setTo_follow(String[] to_follow) {
        this.to_follow = to_follow;
    }
}
