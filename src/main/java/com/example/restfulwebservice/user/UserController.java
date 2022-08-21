package com.example.restfulwebservice.user;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

}
