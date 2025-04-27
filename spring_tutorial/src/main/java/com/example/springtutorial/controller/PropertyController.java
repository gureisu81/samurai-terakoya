package com.example.springtutorial.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertyController {
    @Value("${springtutorial.name:guest}")
    private String propertyName;

    @GetMapping("/property") // ここで確実にコントローラーが動作するようにします
    public String getProperty() {
        return propertyName;
    }
}

