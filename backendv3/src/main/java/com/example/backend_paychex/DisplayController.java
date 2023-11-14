package com.example.backend_paychex;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private ToJSONService toJSONService;
    //provides JSON display to client-side. Displays errors or no?
    @GetMapping("/display")
    public ResponseEntity<String> display(){
        System.out.println(ToJSONService.jsonStr);
        return new ResponseEntity<String>(ToJSONService.jsonStr, HttpStatus.OK); //TODO Convert to JSON string
    }
}
