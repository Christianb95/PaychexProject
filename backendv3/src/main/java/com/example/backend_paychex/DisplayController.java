package com.example.backend_paychex;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.example.backend_paychex.ToJSONService.jsonStr;

//TODO build out display

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api/v3")
public class DisplayController {
    //provides JSON display to client-side. Displays errors or no?
    @GetMapping("/display")
    public String display(){
        return jsonStr;
    } //TODO Make sure this displays correctly
}
