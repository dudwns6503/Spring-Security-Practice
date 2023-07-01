package com.example.securitypractice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test/auth")
    public String test() {
        return "authenticated entry point";
    }
}
