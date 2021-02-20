package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

  @RequestMapping("/")
  public String index() {
    return "Hello Spring Boot!";
  }

}
