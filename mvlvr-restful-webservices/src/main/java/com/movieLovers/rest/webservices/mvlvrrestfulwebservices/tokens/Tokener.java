package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.tokens;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tokens")
public class Tokener {
    @Id @GeneratedValue
    int id;
    private String token;
    private User user;

    public Tokener(String token, User user){
        this.token = token ;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Tokener{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
}
