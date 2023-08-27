package com.movieLovers.rest.webservices.mvlvrrestfulwebservices.testerController;


import org.springframework.web.bind.annotation.*;

// This controller can be used internally to check if the Services are up
@RestController
public class ServiceTester {
    // Endpoint - /testerService
    @RequestMapping(method = RequestMethod.GET, path = "/testerService")
    public String isServiceUp(){
        return "Tester service is up, read Documentations to verify individual Services";
    }

    // Just an easter Egg
    @GetMapping(path = "/easterbunny")
    public EasterEggBean welComeMessage(){
        return new EasterEggBean("Welcome");
    }

    // Personal message Easter Bunny
    @GetMapping(path="/easterbunny/{name}")
    public EasterEggBean generatePersonalMessage(@PathVariable String name){
        return new EasterEggBean("Welcome "+name+ "!");
    }

}
