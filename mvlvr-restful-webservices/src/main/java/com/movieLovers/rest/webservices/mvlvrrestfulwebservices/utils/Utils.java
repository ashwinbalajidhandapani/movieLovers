package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.utils;

import java.security.SecureRandom;

public class Utils {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 16;
    private static Utils utilObject;

    private Utils(){

    }

    public static Utils getInstance(){
        if(utilObject == null){
            utilObject = new Utils();
        }
        return utilObject;
    }
    public String passwordSeperator(String password){
        return password.split(":", 2)[1].split("\"", 3)[1];
    }

    public String TokenGenerator(){
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(randomIndex));
        }

        return token.toString();
    }
}
