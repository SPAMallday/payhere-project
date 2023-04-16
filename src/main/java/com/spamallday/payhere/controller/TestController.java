package com.spamallday.payhere.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
public class TestController {
    @GetMapping
    public String home() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }
}
