package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
