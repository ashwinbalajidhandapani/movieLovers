package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.exceptions.UserNotFoundException;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa.PostRepository;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa.UserRepository;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.security.HasherDehasher;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.security.Validator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserDetailsController {

    private UserDaoServices service;
    private UserRepository repository;
    private PostRepository postRepository;

    private Validator validator = Validator.getObject();
    private HasherDehasher hasher = HasherDehasher.getInstance();

    public UserDetailsController(UserDaoServices service, UserRepository repository, PostRepository postRepository){
        this.service = service;
        this.repository = repository;
        this.postRepository = postRepository;
    }


    @GetMapping(path = "v1/users/email")
    public List<String> fetchUserEmails(){
        return repository.fetchUserEmail();
    }

    @GetMapping(path = "v1/user/{id}")
    public User fetchUserDetailBasedOnId(@PathVariable int id){
        Optional<User> foundUser = repository.findById(id);
        if (foundUser.isEmpty()){
            throw new UserNotFoundException("id "+id);
        }
        return foundUser.get();
    }

    @GetMapping(path="v1/users/isAlreadyRegistered")
    public ResponseEntity<Object> isAlreadyRegistered(@RequestBody String emailId){
        String emailSplit = emailId.split(":", 2)[1].split("\"", 3)[1];

        if(repository.numberOccurencesEmail(emailSplit) == 0){
            return ResponseEntity.accepted().body("{\n\"Message\":\"Email available\"\n}");
        }
        else{
            System.out.println("User Id not available");
            return ResponseEntity.badRequest().body("{\n\"Message\":\"Email not available\"\n}");
        }
    }

    @PostMapping(path = "v1/user")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
        if (repository.numberOccurencesEmail(user.getEmailId()) != 0) {
            return ResponseEntity.badRequest().body("{\n\"Message\":\"Email already Taken\"\n}");
        }

        List<User> users = repository.findAll();
        if(validator.isAlreadyTaken(users, user.getName())){
            return ResponseEntity.badRequest().body("\"{\\n\\\"Message\\\":\\\"Username already Taken\\\"\\n}");
        }
        user.setPassword(hasher.hashPassword(user.getPassword()));
        System.out.println(user.getRole());
        User newUser = repository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location).body(user.toString());
    }




}
