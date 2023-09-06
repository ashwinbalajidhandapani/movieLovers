package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.security;

import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

public class HasherDehasher {

    private static HasherDehasher OBJECT;

    private HasherDehasher(){

    }

    public static HasherDehasher getInstance(){
        if(OBJECT == null){
            OBJECT = new HasherDehasher();
        }
        return OBJECT;
    }

    public String hashPassword(String password){
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8().encode(password);
    }

    public boolean deHashPassword(String password, String hashedPassword){
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8().matches(password, hashedPassword);
    }
}
