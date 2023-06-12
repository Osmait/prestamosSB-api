package com.prestamossb.prestamossbapi.infraestruture.controllers.healcheck;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealCheck {


    @GetMapping
    public ResponseEntity<String> healCheck(){
        return  new ResponseEntity<>("Server Up", HttpStatus.OK);

    }
}
