package com.example.backendv3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO build out display

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/v3")
public class DisplayController {
    //provides JSON display to client-side. Displays errors or no?
    @GetMapping("/display")
    public String display(){
        return "Hello, World";
    }
}
