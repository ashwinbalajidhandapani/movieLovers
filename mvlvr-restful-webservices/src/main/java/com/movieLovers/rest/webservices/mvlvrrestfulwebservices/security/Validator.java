package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.security;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users.User;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users.UserJPAController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Validator {
    public static Validator Object;
    private static final List<String> DOMAINS = new ArrayList<>();
    {
        DOMAINS.add("gmail.com");
        DOMAINS.add("yahoo.com");
        DOMAINS.add("outlook.com");
        DOMAINS.add("hotmail.com");
    }

    private Validator(){

    }

    public static Validator getObject(){
        if(Object == null){
            Object = new Validator();
        }
        return Object;
    }

    public boolean passwordValidator(String password){
        boolean isValidPassword = false;
        if(password.length() < 8 || password.length() > 26) {
            return false;
        }
        for(int i = 0; i < password.length(); i++){
            if(password.charAt(i) == '"' && password.charAt(i) == '\''){
                return false;
            }
            else if(i == password.length() -1){
                isValidPassword = true;
            }
            else continue;
        }
        return isValidPassword;
    }

    public boolean isAlreadyTaken(List<User> users, String username){
        boolean isTaken = false;
        for (User user: users) {
            if(user.getName().equalsIgnoreCase(username)){
                return true;
            }
        }
        return isTaken;
    }

    public boolean validEmail(String userEmail){
        boolean isValidEmail = false;
        String[] splitEmail = userEmail.split("@");

        if(splitEmail.length != 2){
            return false;
        }

        for(int i = 0; i < userEmail.length(); i ++){
            if(userEmail.charAt(i) == '\"' || userEmail.charAt(i) =='\''){
                return false;
            } else if (i == userEmail.length()-1) {
                return true;
            } else{
                continue;
            }
        }
        return isValidEmail;
    }

}
