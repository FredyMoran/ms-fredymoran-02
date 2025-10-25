package com.fredymoran.ms_fredymoran_02.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Microservicio ms-fredymoran-02 ejecutandose correctamente";
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/info")
    public String info() {
        return "ms-fredymoran-02 v1.0.0";
    }
}
