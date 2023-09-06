package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.exceptions.UserNotFoundException;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa.PostRepository;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa.UserRepository;
import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.security.Validator;
import jakarta.validation.Valid;
import org.hibernate.dialect.MySQLDialect;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@RestController
public class UserJPAController {
    private UserDaoServices service;
    private UserRepository repository;
    private PostRepository postRepository;

    private Validator validator = Validator.getObject();

    public UserJPAController(UserDaoServices service, UserRepository repository, PostRepository postRepository){
        this.service = service;
        this.repository = repository;
        this.postRepository = postRepository;
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
        if(!validator.passwordValidator(user.getPassword())){
            return ResponseEntity.badRequest().body("The password must be between 8 and 26 characters long, Don't use single or double quotes");
        }
        else{
            // How the below line works
            /*
            1. we return the user created whenever a new user is added
            2. the user id is then used to build a location path URI from the current Request path
            3. The current request path is localhost:8080/users, inorder to show the location of the created user,
            we have built a new URI; that is localhost:8080/users/{5} - in our case, which can be found in the response header
            * */
            List<User> users = showAllUsersStatic();
            if(validator.isAlreadyTaken(users, user.getName())){
                return ResponseEntity.badRequest().body("User name is already taken");
            }
            System.out.println(validator.validEmail(user.getEmailId()));
            User newUser = repository.save(user);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUser.getId()).toUri();
            return ResponseEntity.created(location).body(user.toString());
        }
    }

    @DeleteMapping(path = "jpa/users/{id}")
    public void deleteUserStatic(@PathVariable int id){
        repository.deleteById(id);
    }

    @GetMapping(path = "jpa/users/posts")
    public List<Post> showAllPosts(){
        return postRepository.findAll();
    }

    @GetMapping(path = "jpa/users/{id}/posts")
    public List<Post> getPostForUser(@PathVariable int id){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id "+id);
        }
        return user.get().getPosts();
    }

    @PostMapping(path="jpa/users/{id}/post")
    public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("id "+id);
        }
        post.setUser(user.get());
        Post newPost = postRepository.save(post);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newPost.getId()).toUri();
        return ResponseEntity.created(location).build();
    }



}
