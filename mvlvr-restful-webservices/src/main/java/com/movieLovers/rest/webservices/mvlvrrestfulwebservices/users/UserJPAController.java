package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.exceptions.UserNotFoundException;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
@RestController
public class UserJPAController {
    private UserDaoServices service;
    private UserRepository repository;

    public UserJPAController(UserDaoServices service, UserRepository repository){
        this.service = service;
        this.repository = repository;
    }
    // GET Method to list all users Static
    @GetMapping(path = "jpa/users")
    public List<User> showAllUsersStatic(){
        return repository.findAll();
    }

    @GetMapping(path = "jpa/users/{id}")
    public User showUserStatic(@PathVariable int id){
        Optional<User> foundUser = repository.findById(id);
        if (foundUser.isEmpty()){
            throw new UserNotFoundException("id "+id);
        }
        return foundUser.get();
    }

    @PostMapping(path = "jpa/usersCreate")
    public ResponseEntity<Object> createUserStatic(@Valid @RequestBody User user){
        User newUser = repository.save(user);
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

    @DeleteMapping(path = "jpa/users/{id}")
    public void deleteUserStatic(@PathVariable int id){
        repository.deleteById(id);
    }
}
