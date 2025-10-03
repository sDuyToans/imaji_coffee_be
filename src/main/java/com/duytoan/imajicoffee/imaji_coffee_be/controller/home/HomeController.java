package com.duytoan.imajicoffee.imaji_coffee_be.controller.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String redirectToHealth() {
        return "App running. Check health at /actuator/health";
    }
}
