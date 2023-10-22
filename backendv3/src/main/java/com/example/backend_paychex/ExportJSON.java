package com.example.backend_paychex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/v3")
public class ExportJSON {
    @Autowired
    private ToJSONService toJSONService;

    @PostMapping("/exportJSON")
    public ResponseEntity<String>exportJSON(){
        System.out.println("In exportJSON controller");
        toJSONService.writeJSON();
        return new ResponseEntity<String>("File saved", HttpStatus.OK);
    }

}
