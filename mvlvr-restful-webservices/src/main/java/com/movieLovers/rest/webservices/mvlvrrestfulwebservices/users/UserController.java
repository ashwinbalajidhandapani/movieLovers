package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.exceptions.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoServices service;

    public UserController(UserDaoServices service){
        this.service = service;
    }
    // GET Method to list all users Static
    @GetMapping(path = "v1/users/")
    public List<User> showAllUsersStatic(){
        return service.listAllUsers();
    }

    @GetMapping(path = "v1/users/{id}")
    public User showUserStatic(@PathVariable int id){
        User foundUser = service.showUserInfoStatic(id);
        if (foundUser == null){
            throw new UserNotFoundException("id "+id);
        }
        return foundUser;
    }

    @PostMapping(path = "v1/users")
    public ResponseEntity<Object> createUserStatic(@Valid @RequestBody User user){
        User newUser = service.addUser(user);
        // How the below line works
        /*
        1. we return the user created whenever a new user is added
        2. the user id is then used to build a location path URI from the current Request path
        3. The current request path is localhost:8080/users, inorder to show the location of the created user,
        we have built a new URI; that is localhost:8080/users/{5} - in our case, which can be found in the response header
        * */
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "v1/users/{id}")
    public void deleteUserStatic(@PathVariable int id){
        service.deleteUser(id);
    }
}
