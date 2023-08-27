package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// All Static as of now, NO JPA or Hibernate used
@Component
public class UserDaoServices {
    private static List<User> users = new ArrayList<>();
    private static Integer count = 0;

    static {
        users.add(new User(++count, "Sharon", LocalDate.now().minusYears(27)));
        users.add(new User(++count, "Kiran", LocalDate.now().minusYears(26)));
        users.add(new User(++count, "Vinoth", LocalDate.now().minusYears(27)));
        users.add(new User(++count, "Ashwin", LocalDate.now().minusYears(28)));
    }

    // list all users
    public List<User> listAllUsers(){
        return users;
    }

    public User showUserInfoStatic(int id){
        for (User user: users) {
            if(user.getId() == id){
                return user;
            }
            else{
                continue;
            }
        }
        return null;
    }

    // Save User
    public User addUser(User user){
        user.setId(++count);
        users.add(user);
        return user;
    }

    // delete user
    public void deleteUser(int id){
        for (User user: users) {
            if(user.getId() == id){
                users.remove(user);
            }
            else{
                continue;
            }
        }
    }

}
