package com.example.backend_paychex;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//TODO build out display

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/v3")
public class DisplayController {
    //provides JSON display to client-side. Displays errors or no?
    @GetMapping("/display")
    public ResponseEntity<String> display(){
        return new ResponseEntity<String>(ToJSONService.topFiveJSON, HttpStatus.OK); //TODO Convert to JSON string
    }
}
