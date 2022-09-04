package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue findAllUsers() {

        List<User> users = service.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "name", "joinDate", "password");
        //User에서 지정 한 JsonFilter 참조값 가져와서
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);
        return mapping;
    }

    //@GetMapping("/v1/users/{id}") // v1
    //@GetMapping(value = "/users/{id}", params ="version=1")
   // @GetMapping(value = "/users/{id}", headers ="X-API-VERSION=1")
    @GetMapping(value = "/v1/users/{id}",produces = "application/vnd.company.apply1+json")
    public MappingJacksonValue findUserV1(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "name", "password", "ssn");
        //User에서 지정 한 JsonFilter 참조값 가져와서
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV1", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);
        return mapping;


    }

    //@GetMapping("/v2/users/{id}")
    //@GetMapping(value = "/users/{id}", params ="version=2")
    //@GetMapping(value = "/users/{id}", headers ="X-API-VERSION=2")
    @GetMapping(value = "/v2/users/{id}",produces = "application/vnd.company.apply2+json")
    public MappingJacksonValue findUserV2(@PathVariable int id) {
        User user = service.findOne(id);

        if (user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        //User-> user 2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(
                "id", "name", "password", "ssn");
        //User에서 지정 한 JsonFilter 참조값 가져와서

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);
        return mapping;
    }


}
