package com.example.restfulwebservice.helloworld;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor //default 생성자
public class HelloWorldBean {
    private String message;

}
