package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersionController {

    // Versioning based on URI
    @GetMapping("v1/person")
    public PersonV1 showPersonDetailsVerOne(){
        return new PersonV1("Ashwin Balaji");
    }

    @GetMapping("v2/person")
    public PersonV2 showPersonDetailsVertwo(){
        return new PersonV2(new Name("Sharon", "Appoline"));
    }

    // Versioning based on Parameters
    @GetMapping(value = "/person", params = "version=v1")
    public PersonV1 showPersonDetailsVerOneParamOne(){
        return new PersonV1("Ashwin Balaji");
    }
    // We should give the below in the accept header
    @GetMapping(value = "/person", produces = "application/mvlvrs.v1.fnln")
    public PersonV2 showPersonDetailsVertwoParamTwo(){
        return new PersonV2(new Name("Sharon", "Appoline"));
    }
}
