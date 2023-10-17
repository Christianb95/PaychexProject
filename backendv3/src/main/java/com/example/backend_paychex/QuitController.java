package com.example.backend_paychex;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

import static com.example.backend_paychex.ConnectToDB.closeConnection;

@RestController
@CrossOrigin
@RequestMapping("/api/v3")
public class QuitController {
    //Closes the connection and terminates the backend
    @PostMapping("/quit")
    public ResponseEntity<String>quit() {
        try {
            closeConnection();
            return ResponseEntity.ok("You have successfully quit.");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
