package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "SELECT emailId FROM user_details")
    List<String> fetchUserEmail();

    @Query(value = "SELECT count(*) FROM user_details WHERE email_id = ?1", nativeQuery = true)
    Long numberOccurencesEmail(String email_id);
}
