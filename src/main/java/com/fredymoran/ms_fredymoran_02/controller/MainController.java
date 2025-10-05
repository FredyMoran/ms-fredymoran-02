package com.fredymoran.ms_fredymoran_02.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MainController {

    @GetMapping("/health")
    public String healthCheck() {
        return "Servicio corriendo correctamente - ms-fredymoran-02";
    }

    @GetMapping("/test")
    public String test() {
        return "Microservicio funcionando correctamente!";
    }
}