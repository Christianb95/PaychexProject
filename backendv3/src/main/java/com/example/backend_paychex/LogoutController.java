package com.example.backend_paychex;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v3")
public class LogoutController {
    //Closes the connection and terminates the backend
    @Autowired
    Logout logout;
    @PostMapping("/logout")
    public ResponseEntity<String>logout(){
        try {
            logout.closeConnection();
            return new ResponseEntity<String>("Closed Connection", HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed: " + e.getMessage());
        }
    }
}
