package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.jpa;

import com.movieLovers.rest.webservices.mvlvrrestfulwebservices.tokens.Tokener;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Tokener, String> {

}
