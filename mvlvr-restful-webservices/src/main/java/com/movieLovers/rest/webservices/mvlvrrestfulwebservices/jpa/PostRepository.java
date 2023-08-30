package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
