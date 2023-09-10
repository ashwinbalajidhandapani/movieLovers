package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.exceptions.UserNotFoundException;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa.PostRepository;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa.TokenRepository;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa.UserRepository;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.security.HasherDehasher;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.security.Validator;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.tokens.Tokener;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.utils.Utils;
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
    private TokenRepository tokenRepository;

    private Validator validator = Validator.getObject();
    private HasherDehasher hasher = HasherDehasher.getInstance();

    private Utils utils = Utils.getInstance();

    public UserDetailsController(UserDaoServices service, UserRepository repository, PostRepository postRepository, TokenRepository tokenRepository){
        this.service = service;
        this.repository = repository;
        this.postRepository = postRepository;
        this.tokenRepository = tokenRepository;
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
        User newUser = repository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
        return ResponseEntity.created(location).body(user.toString());
    }

    @PutMapping(path = "v1/user/password/{id}")
    public ResponseEntity<Object> updateUserDetails(@RequestBody String password, @PathVariable int id){
        Optional<User> searchResult = repository.findById(id);
        if(searchResult.isEmpty()) return ResponseEntity.notFound().build();
        else{
            User foundUser = fetchUserDetailBasedOnId(id);
            foundUser.setPassword(hasher.hashPassword(utils.passwordSeperator(password)));
            return ResponseEntity.accepted().build();
        }
    }

    @PostMapping(path = "v1/login")
    public ResponseEntity<Object> login(@RequestBody User user) throws InterruptedException {
        if(user.getName() != null || user.getRole() != null || user.getBirthdate() != null){
            return ResponseEntity.badRequest().body("Enter only email and password as part of the request");
        }
        String userEmail = user.getEmailId();
        String userPassword = user.getPassword();
        List<User> savedUsers = repository.findAll();
        for(int i = 0; i < savedUsers.size(); i++){
            if(savedUsers.get(i).getEmailId().equals(userEmail)){
                if(hasher.deHashPassword(userPassword, savedUsers.get(i).getPassword())){
                    return ResponseEntity.accepted().body("Authentication success -- Authentication Token - "+createTokenRecord(user));
                }
                else{
                    return ResponseEntity.badRequest().body("Check username and Password");
                }
            }
            else if(i < savedUsers.size()-1){
                continue;
            }
            else{
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.accepted().build();
    }

    @PostMapping(path = "v1/createToken")
    public String createTokenRecord(User user) throws InterruptedException {
        System.out.println("generating Token");
        String accessToken = utils.TokenGenerator();
//        Thread.sleep(5000);
        tokenRepository.save(new Tokener(accessToken, user));
        return accessToken;

    }

}
