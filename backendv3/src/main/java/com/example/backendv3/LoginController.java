package com.example.backendv3;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.SQLException;

//RestAPI receives login info from frontend in RequestBody
@RestController
@CrossOrigin
@RequestMapping("/api/v3")
public class LoginController {

    @PostMapping("/login")
    public ResponseEntity<String>login(@RequestBody ConnectToDB connectToDB) {
        /*  Input: RequestBody mapped to DTO ConnectToDB
            Output: ResponseEntity for status
            Calls createConnection from ConnectToDB class to create database connection
        */
        try {
            connectToDB.createConnection(); //establishes database connection
            return ResponseEntity.ok("Login Successful");

        } catch (SQLException | ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());

        }finally{
            try {
                connectToDB.closeConnection();
            } catch (SQLException e){

            }
        }
    }
}
