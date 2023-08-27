package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.testerController;

public class EasterEggBean {
    private String message;

    public EasterEggBean(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
